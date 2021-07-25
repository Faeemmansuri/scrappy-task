import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EventService {
  constructor(private http: HttpClient) {}

  getEvents(startAt, maxResults, requestBody: any): Observable<any> {
    const endpoint = 'http://localhost:8080/events';
    let queryString = '';
    if (startAt && maxResults) {
      queryString = `?startAt=${startAt}&maxResults=${maxResults}`;
    }
    return this.http.post(endpoint + queryString, requestBody);
  }
}
