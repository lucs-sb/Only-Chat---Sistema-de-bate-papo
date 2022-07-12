import { Component, OnInit } from '@angular/core';
import { FriendsService } from '../friends.service';
import { Friend } from 'src/app/Friend';
import { NotifierService } from 'angular-notifier';

@Component({
  selector: 'app-friends',
  templateUrl: './friends.component.html',
  styleUrls: ['./friends.component.css']
})
export class FriendsComponent implements OnInit {
  private notifier: NotifierService;
  removeFriendId = 0;
  friends: Friend[] = [];

  /**
   * Constructor
   *
   * @param {NotifierService} notifier Notifier service
   */
  constructor(private friendsService: FriendsService, notifier: NotifierService) {
    this.notifier = notifier;
    this.getFriends();
  }

  ngOnInit(): void {
    this.getFriends();
  }

  getFriends(): void {
    this.friendsService.getFriends().subscribe((friends) => (this.friends = friends));
  }

  setRemoveId(event: { target: any; srcElement: any; currentTarget: any; }) {
    var target = event.target || event.srcElement || event.currentTarget;
    this.removeFriendId = target.attributes.id.value;
    console.log(this.removeFriendId)
  }

  sendRemoveFriend() {
    this.friendsService.sendRemoveFriend(this.removeFriendId).subscribe(res => {
      this.notifier.notify('success', 'Operação realizada com sucesso');

      this.friends = this.friends.filter(item => item.id != this.removeFriendId);
    })
  }

}
