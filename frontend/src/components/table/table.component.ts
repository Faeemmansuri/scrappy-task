import { Component, OnInit, VERSION } from '@angular/core';
import { DataService } from '../../services/data.service';
import { EventService } from '../../services/event.service';

@Component({
  selector: 'table-app',
  templateUrl: './table.component.html'
})
export class TableComponent implements OnInit {
  headers = ['Event Name', 'Event Type', 'Start Date', 'End Date', 'Location'];
  events = [];

  constructor(private dataService: DataService) {}

  ngOnInit(): void {
    this.dataService.sharedEvents.subscribe((res: any) => {
      this.events = res?.res?.data ? res.res.data : [];
    });
  }
}
