package Cluedo;

import Cluedo.Card.PersonCard;
import Cluedo.Card.RoomCard;
import Cluedo.Card.WeaponCard;

public class Suggestion{
    PersonCard.PersonType person;
    RoomCard.RoomType room;
    WeaponCard.WeaponType weapon;

    Suggestion(PersonCard.PersonType person, RoomCard.RoomType room, WeaponCard.WeaponType weapon) {
        this.person = person;
        this.room = room;
        this.weapon = weapon;
    }
}
