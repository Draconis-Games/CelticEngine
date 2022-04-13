package com.draconisgames.celticengine.game;

import com.draconisgames.celticengine.physics.math.Transform;
import com.draconisgames.celticengine.rendering.Renderable;

import java.util.ArrayList;
import java.util.List;

public abstract class GameObject {
    Transform transform = new Transform();
    List<Renderable> renderables = new ArrayList<>();

    public void init(){
        renderables.forEach(Renderable::bindVbos);
    }

    public void bind(){
        renderables.forEach(Renderable::bind);
    }

    public void render(){
        renderables.forEach(Renderable::render);
    }

    public void delete(){
        renderables.forEach(Renderable::delete);
    }
}
