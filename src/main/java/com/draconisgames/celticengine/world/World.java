package com.draconisgames.celticengine.world;

import com.draconisgames.celticengine.game.Camera;
import com.draconisgames.celticengine.game.GameObject;
import com.draconisgames.celticengine.physics.math.Transform;

import java.util.ArrayList;
import java.util.List;

public class World {

	List<GameObject> gameObjects = new ArrayList<>();

	private Camera activeCam;

	public void addGameObject(GameObject... gameObject){
		gameObjects.addAll(List.of(gameObject));
	}

	public void load(){
		if (activeCam == null) {
			activeCam = new Camera(new Transform(), 1, 70);
			gameObjects.add(activeCam);
		}
		gameObjects.forEach(GameObject::init);
	}

	public void render(){
		gameObjects.forEach(GameObject::render);
	}
	public void cleanup(){
		gameObjects.forEach(GameObject::delete);
	}

	public Camera getActiveCam() {
		return activeCam;
	}

	public void setActiveCam(Camera activeCam) {
		this.activeCam = activeCam;
		if (!gameObjects.contains(activeCam)) {
			gameObjects.add(activeCam);
		}
	}
}
