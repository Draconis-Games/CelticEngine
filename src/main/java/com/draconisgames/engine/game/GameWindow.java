package com.draconisgames.engine.game;

import com.draconisgames.engine.physics.math.matrices.Matrix4f;
import com.draconisgames.engine.rendering.ImageLoader;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public abstract class GameWindow {

    protected int width, height;

    protected float delta;
    protected long lastFrameTime;
    protected long time;
    protected int fps;

    private GLFWWindowSizeCallback sizeCallback;

    private String title;

    public Matrix4f projectionMatrix;

    protected boolean fullScreen;
    protected boolean isResized;
    protected boolean resizable = true;

    private long window;

    public GameWindow(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public GameWindow(int width, int height, String title, boolean resizeable) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.resizable = resizeable;
    }

    public void run() {
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);

        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create GameWindow");
        }

        createCallbacks();

        try ( MemoryStack stack = stackPush() ) {
			IntBuffer widthPtr = stack.mallocInt(1);
			IntBuffer heightPtr = stack.mallocInt(1);

			glfwGetWindowSize(window, widthPtr, heightPtr);

			GLFWVidMode screen = glfwGetVideoMode(glfwGetPrimaryMonitor());

			glfwSetWindowPos(
					window,
					(screen.width() - widthPtr.get(0)) / 2,
					(screen.height() - heightPtr.get(0)) / 2
			);
		}

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);

        glfwShowWindow(window);

    }

    public void setIconImage(String path) {
        GLFWImage image = GLFWImage.malloc(); GLFWImage.Buffer imagebf = GLFWImage.malloc(1);
        ImageLoader imageLoader = ImageLoader.loadImage(path);
        image.set(imageLoader.getWIDTH(), imageLoader.getHEIGHT(), imageLoader.getIMAGE());
        imagebf.put(0, image);

        glfwSetWindowIcon(window, imagebf);
    }

    public void createCallbacks() {
        sizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                setDimensions(width, height);
            }
        };

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true);
		});

    }

    private void setDimensions(int w, int h) {
        width = w;
        height = h;
        isResized = true;
    }

    //temp


    public long getWindow() {
        return window;
    }

}