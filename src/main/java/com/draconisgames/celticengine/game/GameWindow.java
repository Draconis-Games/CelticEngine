package com.draconisgames.celticengine.game;

import com.draconisgames.celticengine.physics.math.matrices.Matrix4f;
import com.draconisgames.celticengine.file.ImageLoader;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public abstract class GameWindow {

    protected int width, height;

    protected float delta;
    private long lastFrameTime;
    protected int fps;

    private GLFWWindowSizeCallback sizeCallback;

    private final String title;

    public Matrix4f projectionMatrix;

    protected boolean fullScreen;
    protected boolean isResized;
    protected boolean resizable = true;

    protected long window;

    public GameWindow(int width, int height, String title) {
        this(width, height, title, true);
    }

    public GameWindow(int width, int height, String title, boolean resizeable) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.resizable = resizeable;

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
    }

    public void run() {

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

        loop();

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

        glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.out));

    }

    private void setDimensions(int w, int h) {
        width = w;
        height = h;
        isResized = true;
    }

    private void loop() {
        GL.createCapabilities();

        lastFrameTime = System.nanoTime();

        init();

        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents();
            update(delta);
            delta = ((float) System.nanoTime() - lastFrameTime) / 1000000000f;
            fps = Math.round(1000000000f / (System.nanoTime() - lastFrameTime));
            lastFrameTime = System.nanoTime();
        }
        remove();
    }

    public void remove() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public abstract void update(float delta);

    public abstract void init();

}
