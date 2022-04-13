package com.draconisgames.celticengine.file;

import com.draconisgames.celticengine.physics.math.Vector3;
import com.draconisgames.celticengine.rendering.Mesh;
import de.javagl.obj.Obj;
import de.javagl.obj.ObjData;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

public class ModelLoader {
    private static final Logger logger = LogManager.getLogger(ModelLoader.class);

    public static Mesh load(String path){
        URL url = TextLoader.class.getClassLoader().getResource(path);

        if (url == null) {
            logger.error("File not found: " + path);
            return null;
        }

        try {
            InputStream inputStream = new FileInputStream(new File(url.toURI()));
            Obj obj = ObjUtils.convertToRenderable(ObjReader.read(inputStream));
            return new Mesh(ObjData.getFaceVertexIndices(obj), ObjData.getVertices(obj), ObjData.getTexCoords(obj, 2), ObjData.getNormals(obj));
        } catch (URISyntaxException | IOException e) {
            logger.error("Failed to load file: " + path);
            return null;
        }
    }
}
