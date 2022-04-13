package com.draconisgames.celticengine.rendering;

public interface Renderable {
    void bindVbos();
    void bind();
    void render();
    void delete();
}
