import { Component, OnInit } from '@angular/core';
import { NotifierService } from 'angular-notifier';
import { AddfriendService } from '../addfriend.service';
import { User } from '../user';

@Component({
  selector: 'app-addfriend',
  templateUrl: './addfriend.component.html',
  styleUrls: ['./addfriend.component.css']
})
export class AddfriendComponent implements OnInit {
  private notifier: NotifierService;
  friends: User[] = [];
  findName: string = '';

  /**
   * Constructor
   *
   * @param {NotifierService} notifier Notifier service
   */
  constructor(private addfriendService: AddfriendService, notifier: NotifierService) {
    this.notifier = notifier;
  }

  ngOnInit(): void {
    this.addfriendService.getAllNoFriends().subscribe(friends => (this.friends = friends));
  }

  findNewFriends() {
    this.friends = [];
    this.addfriendService.findNewsFriend(this.findName).subscribe((friends) => (this.friends = friends));
  }

  addfriend(event: { target: any; srcElement: any; currentTarget: any; }) {
    var target = event.target || event.srcElement || event.currentTarget;
    var addFriendId = target.attributes.id.value;

    this.addfriendService.addfriend(addFriendId).subscribe(res => {
      this.friends = this.friends.filter(item => item.id != addFriendId);
      this.notifier.notify('success', 'Amigo adicionado com sucesso');
    })

  }

}