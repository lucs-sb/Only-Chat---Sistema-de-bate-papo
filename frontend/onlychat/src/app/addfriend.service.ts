import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class AddfriendService {

  constructor(private http: HttpClient) { }

  private API_USER_ME: string = `https://web-only-chat.herokuapp.com/api/${localStorage.getItem("userId")}`;
  private API_ADD_USER: string = `https://web-only-chat.herokuapp.com/api/${localStorage.getItem("userId")}/newcontact`;

  findNewsFriend(findString: any): Observable<User[]> {
    {
      const httpOptions = {
        headers: { authorization: 'Basic ' + localStorage.getItem("authorization") },
        params: { 'adicionar': findString }
      };

      return this.http.get<User[]>(this.API_USER_ME, httpOptions)
    }
  }

  addfriend(id: any): Observable<any> {
    {
      return this.http.post<any>(this.API_ADD_USER, { 'id': id })
    }
  }

  getAllNoFriends(): Observable<User[]> {
    {
      return this.http.get<User[]>(this.API_ADD_USER)
    }
  }
}
