import { Component, OnInit } from '@angular/core';
import { DataService } from '../../services/data.service';
import { EventService } from '../../services/event.service';

// import { faSearch } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'pagination-filter',
  templateUrl: './pagination.component.html',
})
export class PaginationComponent implements OnInit {
  startAt = 0;
  maxResults = 20;
  currentPage = 1;
  filter = {};
  total = 0;
  pages: number[] = [];

  constructor(
    private dataService: DataService,
    private eventService: EventService
  ) {}

  ngOnInit(): void {
    this.dataService.sharedEvents.subscribe((res: any) => {
      this.total = res?.res?.total ? res.res.total : this.total;
      this.startAt = res?.res?.startAt ? res.res.startAt : this.startAt;
      this.maxResults = res?.res?.maxResults
        ? res.res.maxResults
        : this.maxResults;
      this.filter = res?.filter ? res.filter : this.filter;
      this.pages = this.generatePagesList(this.total);
    });
  }

  generatePagesList(total: number): number[] {
    const pageList = [];
    const pages = total / 20;
    for (let i = 1; i < pages; i++) {
      pageList.push(i);
    }

    if (total % this.maxResults > 0) {
      pageList.push(pageList.length + 1);
    }
    return pageList;
  }

  onPageClick($event: any) {
    const pageNo = $event.target.getAttribute('area-id');
    this.currentPage = pageNo;
    this.startAt = (pageNo - 1) * this.maxResults;
    this.eventService
      .getEvents(this.startAt, this.maxResults, this.filter)
      .subscribe((res) => {
        this.dataService.setEvents({ res: res, filter: this.filter });
      });
    console.log('onPageClick', pageNo);
  }

  previousPage() {
    this.currentPage -= 1;
    this.startAt = this.currentPage * this.maxResults;
    this.eventService
      .getEvents(this.startAt, this.maxResults, this.filter)
      .subscribe((res) => {
        this.dataService.setEvents({ res: res, filter: this.filter });
      });
  }

  nextPage() {
    this.currentPage += 1;
    this.startAt = this.currentPage * this.maxResults;
    this.eventService
      .getEvents(this.startAt, this.maxResults, this.filter)
      .subscribe((res) => {
        this.dataService.setEvents({ res: res, filter: this.filter });
      });
  }
}
