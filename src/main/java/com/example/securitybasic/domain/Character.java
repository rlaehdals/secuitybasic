package com.example.securitybasic.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "character_name")
    private String name;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


    private void setUser(User user){
        this.user=user;
    }

    private void addCharacter(){
        user.getCharacters().add(this);
    }

    public void update(String name){
        this.name=name;
    }

    public static Character createCharacter(String name, User user){
        Character character = new Character();
        character.setUser(user);
        character.addCharacter();
        character.name=name;
        return character;
    }
}
