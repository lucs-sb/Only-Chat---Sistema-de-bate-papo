import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { StorageService } from './storage.service';
import { Router } from '@angular/router';

@Injectable()
export class HttpBasicAuthInterceptor implements HttpInterceptor {
  constructor(private storage: StorageService, private router: Router) { }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    console.log('HttpBasicAuthInterceptor - Test');
    const credentials = this.storage.get('authorization');

    if (credentials) {
      console.log('HttpBasicAuthInterceptor - credentials: ' + credentials);
      return next.handle(request.clone({ setHeaders: { Authorization: 'Basic ' + credentials } }))
        .pipe(
          catchError((response: HttpErrorResponse) => {
            if (response.status === 401) {
              this.storage.logoutUser()
              this.router.navigateByUrl(`/login`);
            }
            return throwError(response);
          }
          )
        )
    }
    else return next.handle(request);
  }
}
