package com.faeem.scrapper.services;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.faeem.scrapper.dto.EventDTO;
import com.faeem.scrapper.repository.EventRepository;

@Service
public class ScrapServiceImpl implements ScrapService {

	private static final Logger log = LoggerFactory.getLogger(ScrapServiceImpl.class);

	private static final int INSERT_BATCH_SIZE = 100;

	private static final List<String> EVENT_SOURCE_URIS = Arrays.asList("https://www.techmeme.com/events",
			"https://www.computerworld.com/article/3313417/tech-event-calendar-shows-conferences-and-it-expos-updated.html");

	@Autowired
	private EventRepository eventRepository;

	@Async
	public void scrapEvents(String sourceUrl) {
		Document doc = null;
		try {
			List<EventDTO> eventList = new ArrayList<>();
			for (String sourceUri : EVENT_SOURCE_URIS) {
				doc = Jsoup.connect(sourceUri).get();
				String sourceHost = doc.baseUri().split("/")[2];

				if (sourceHost.equals("www.techmeme.com")) {
					Elements events = doc.getElementsByClass("rhov");
					eventList.addAll(scrapTechmeme(events, sourceHost));
				} else if (sourceHost.equals("www.computerworld.com")) {
					Elements events = doc.getElementsByTag("tr");
					eventList.addAll(scrapComputerWorld(events, sourceHost));
				}
			}

			List<List<EventDTO>> partitions = ListUtils.partition(eventList, INSERT_BATCH_SIZE);
			for (List<EventDTO> partition : partitions) {
				int insertedRecors = eventRepository.saveAll(partition);
				log.info("Records inserted : {}", insertedRecors);
			}

		} catch (IOException ioe) {
			log.error(ioe.getMessage(), ioe);
		}
	}

	private List<EventDTO> scrapTechmeme(Elements events, String sourceHost) {
		List<EventDTO> eventDtoList = new ArrayList<>();
		String currentYear = String.valueOf(LocalDateTime.now().getYear());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MMM-dd");

		for (Element event : events) {
			Elements allElements = event.getAllElements();
			EventDTO eventDto = new EventDTO();
			for (int index = 0; index < allElements.size(); index++) {
				Element element = allElements.get(index);
				if (index == 1 && element.tagName().equals("a")) {
					eventDto.setPageUrl("https://" + sourceHost + element.attr("href"));
				} else if (index == 2 && element.tagName().equals("div")) {
					String[] startEndDate = element.text().split("-");

					String[] startMonthDate = startEndDate[0].split(" ");
					String startMonth = startMonthDate[0];
					String startDate = startMonthDate[1];

					String endMonth = startMonth;
					String endDate = startDate;
					if (startEndDate.length == 2 && startEndDate[1].split(" ").length == 2) {
						endMonth = startEndDate[1].split(" ")[0];
						endDate = startEndDate[1].split(" ")[1];
					}

					try {
						eventDto.setStartDate(df.parse(currentYear + "-" + startMonth + "-" + startDate));
						eventDto.setEndDate(df.parse(currentYear + "-" + endMonth + "-" + endDate));
					} catch (ParseException pe) {
						log.error(pe.getMessage(), pe);
					}
				} else if (index == 3) {
					String[] eventTypeName = element.text().split(":");
					if (eventTypeName.length == 2) {
						eventDto.setType(eventTypeName[0].trim());
						eventDto.setName(eventTypeName[1].trim());
					} else {
						eventDto.setType("N/A");
						eventDto.setName(eventTypeName[0].trim());
					}
				} else if (index == 5) {
					eventDto.setLocation(element.text());
				}
			}
			eventDtoList.add(eventDto);
		}

		return eventDtoList;
	}

	private List<EventDTO> scrapComputerWorld(Elements events, String sourceHost) {
		List<EventDTO> eventDtoList = new ArrayList<>();
		events.remove(0);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		for (Element event : events) {
			Elements allElements = event.getAllElements();
			EventDTO eventDto = new EventDTO();
			for (int index = 0; index < allElements.size(); index++) {
				Element element = allElements.get(index);
				if (element.tagName().equals("tr"))
					continue;
				try {
					if (index == 2 && element.tagName().equals("a")) {
						eventDto.setPageUrl(element.attr("href"));
						eventDto.setName(element.attr("title"));
					} else if (index == 4) {
						eventDto.setStartDate(df.parse(element.text()));
					} else if (index == 5) {
						eventDto.setEndDate(df.parse(element.text()));
					} else if (index == 6) {
						eventDto.setLocation(element.text());
					}
				} catch (ParseException pe) {
					log.error(pe.getMessage(), pe);
				}
			}
			eventDtoList.add(eventDto);
		}

		return eventDtoList;
	}
}
