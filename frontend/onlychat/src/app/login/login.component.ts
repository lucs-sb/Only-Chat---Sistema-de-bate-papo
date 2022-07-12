import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NotifierService } from 'angular-notifier';
import { LoginService } from '../login.service';
import { StorageService } from '../storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  private notifier: NotifierService;
  email: string = '';
  password: string = '';

  /**
 * Constructor
 *
 * @param {NotifierService} notifier Notifier service
 */
  constructor(private loginService: LoginService,
    private router: Router,
    private localStorage: StorageService, notifier: NotifierService) {
    this.notifier = notifier;
  }


  ngOnInit(): void {
  }

  login() {
    try {
      if (!this.password || !this.email)
        throw new Error('Preencha todos os campos');

      var teste = this.loginService.login(this.email, this.password).subscribe((user) => {
        this.localStorage.set('authorization', btoa(this.email + ':' + this.password));
        this.router.navigate(['/friends']);
      }, (error) => {
        this.notifier.notify('error', 'Informações inválidas');
      });

    }
    catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }

}
