package com.draconisgames.celticengine.world;

import com.draconisgames.celticengine.game.GameObject;

import java.util.ArrayList;
import java.util.List;

public class World {

	List<GameObject> gameObjects = new ArrayList<>();


	public void addGameObject(GameObject... gameObject){
		gameObjects.addAll(List.of(gameObject));
	}

	public void load(){
		gameObjects.forEach(GameObject::init);
	}

	public void render(){
		gameObjects.forEach(GameObject::render);
	}
	public void cleanup(){
		gameObjects.forEach(GameObject::delete);
	}

}
