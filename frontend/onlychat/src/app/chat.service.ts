import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Friend } from './Friend';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  constructor(private http: HttpClient) { }
  private API_USER_ME: string = `http://localhost:8082/api/user/${localStorage.getItem("userId")}/contact`;

  getFriendForCard(id: any): Observable<Friend> {
    {
      const httpOptions = {
        headers: { authorization: 'Basic ' + localStorage.getItem("authorization") },
        params: { 'email': "wesley" }
      };

      return this.http.get<any>(this.API_USER_ME, httpOptions).pipe(
        tap(response => {
          return response
        })
      );
    }
  }
}
