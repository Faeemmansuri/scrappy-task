import { Component, OnInit } from '@angular/core';
import { DataService } from '../../services/data.service';
import { EventService } from '../../services/event.service';

// import { faSearch } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'event-filter',
  templateUrl: './filter.component.html',
})
export class FilterComponent implements OnInit {
  // faSearch = faSearch;
  eventTypePlaceholder = 'Select event types';
  eventTypes = [
    { name: 'HYBRID', checked: false },
    { name: 'VIRTUAL', checked: false },
    { name: 'Earnings', checked: false },
  ];
  eventName: string = '';
  startDate: string = '';
  endDate: string = '';
  eventType: string[] = [];
  location: string = '';

  constructor(
    private eventService: EventService,
    private dataService: DataService
  ) {}

  ngOnInit(): void {
    const requestBody = {};
    this.eventService.getEvents(0, 20, requestBody).subscribe((res) => {
      this.dataService.setEvents({ res: res, filter: requestBody });
      console.log('Data : ', res);
    });
  }

  converToRequestFormat(dateString: string | null | undefined) {
    if (dateString === null || dateString === '' || dateString === undefined) {
      return null;
    }
    const date = new Date(dateString);
    console.log('converToRequestFormat', date);

    return `${date.getDate()}/${date.getMonth() + 1}/${date.getFullYear()}`;
  }

  onSearch(): void {
    console.log(this.endDate);
    const requestBody = {
      type: this.eventType,
      startDate: this.converToRequestFormat(this.startDate),
      endDate: this.converToRequestFormat(this.endDate),
      location: this.location,
      eventName: this.eventName,
    };
    this.eventService.getEvents(0, 20, requestBody).subscribe((res) => {
      this.dataService.setEvents({ res: res, filter: requestBody });
      console.log('Data : ', res);
    });
  }

  shareCheckedList(selectedItems: any[]) {
    this.eventType = selectedItems;
  }
  shareIndividualCheckedList(item: {}) {
    console.log('shareIndividualCheckedList', item);
  }
}
