import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tap } from 'rxjs/operators';
import { StorageService } from './storage.service';
import { User } from './user';


@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private API_USER_ME: string = 'https://web-only-chat.herokuapp.com/api/login';

  constructor(private http: HttpClient, private localStorage: StorageService) { }

  login(username: string, password: string) {

    const httpOptions = {
      headers: { authorization: 'Basic ' + btoa(username + ':' + password) }
    };

    return this.http.get<User>(this.API_USER_ME+`/${username}`, httpOptions).pipe(
      tap((response: User) => {
        this.localStorage.set('userId', response.id.toString());
        this.localStorage.set('userEmail', response.email);
        this.localStorage.set('userName', response.name);
        this.localStorage.set('userGender', response.gender);
        this.localStorage.set('userUrl_photo', response.url_photo);

        return response
      })
    );
  }

  logout() {
    this.localStorage.clear()
  }
}
