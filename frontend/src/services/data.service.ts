import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  private events = new BehaviorSubject({});
  sharedEvents = this.events.asObservable();

  constructor() {}

  setEvents(events: any) {
    this.events.next(events);
  }
}
