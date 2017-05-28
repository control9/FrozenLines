package com.control9.lines.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;

import static com.control9.lines.LinesConstants.FIELDSIZE_Y;
import static com.control9.lines.LinesConstants.FIELD_WITH_SIDEBAR_X;

public class GraphicHolder {
    private static final float FONT_SCALING = 0.009f;
    public static SpriteBatch batch;
    public static OrthographicCamera cam;
    public static float widthU = Gdx.graphics.getWidth() / FIELD_WITH_SIDEBAR_X;
    public static float heightU = Gdx.graphics.getHeight() / FIELDSIZE_Y;
    public static AssetManager manager = new AssetManager();
    public static BitmapFont font;
    public static GlyphLayout glyphLayout;
    public static Matrix4 normalProjection = new Matrix4();
    public static FieldRenderer FIELD_RENDERER;
    public static GameOverRenderer GAMEOVER_RENDERER;
    public static SidebarRenderer SIDEBAR_RENDERER;

    public static void init() {
        manager = new AssetManager();
        if (batch != null) batch.dispose();
        batch = new SpriteBatch();
        cam = new OrthographicCamera(FIELD_WITH_SIDEBAR_X, FIELDSIZE_Y);
        Texture.setAssetManager(manager);
        TextureParameter tparam = new TextureParameter();
        tparam.genMipMaps = true;
        tparam.magFilter = TextureFilter.MipMapLinearLinear;
        tparam.minFilter = TextureFilter.MipMapLinearLinear;
        loadTextures(tparam);
        manager.finishLoading();

        BitmapFontData bitmapFontData = new BitmapFontData(Gdx.files.internal("data/font.fnt"), false);
        font = new BitmapFont(bitmapFontData,
                new TextureRegion(manager.get("data/font.png", Texture.class)), true);
        FIELD_RENDERER = new FieldRenderer();
        GAMEOVER_RENDERER = new GameOverRenderer();
        SIDEBAR_RENDERER = new SidebarRenderer();
        resizeCam(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private static void loadTextures(TextureParameter tparam) {
        manager.load("data/gameover.png", Texture.class, tparam);
        manager.load("data/cell.png", Texture.class, tparam);
        manager.load("data/ball.png", Texture.class, tparam);
        manager.load("data/glow.png", Texture.class, tparam);
        manager.load("data/sidebar-bg.png", Texture.class, tparam);
        manager.load("data/score.png", Texture.class, tparam);
        manager.load("data/level.png", Texture.class, tparam);
        manager.load("data/record.png", Texture.class, tparam);
        manager.load("data/replay.png", Texture.class, tparam);
        manager.load("data/font.png", Texture.class, tparam);
    }

    public static void resizeCam(int width, int height) {
        normalProjection.setToOrtho2D(0, 0, width, height);
        float ppu = 0;
        if (width * FIELDSIZE_Y > height * FIELD_WITH_SIDEBAR_X) {
            heightU = FIELDSIZE_Y;
            widthU = (width * FIELDSIZE_Y) / (height + 0f);
            ppu = height / (FIELDSIZE_Y + 0f);
        } else {
            widthU = FIELD_WITH_SIDEBAR_X;
            heightU = (height * FIELD_WITH_SIDEBAR_X) / (width + 0f);
            ppu = width / (FIELD_WITH_SIDEBAR_X + 0f);
        }
        cam.setToOrtho(false, widthU, heightU);
        cam.position.set((cam.viewportWidth - width / ppu + FIELD_WITH_SIDEBAR_X) / 2,
                (cam.viewportHeight - height / ppu + FIELDSIZE_Y) / 2, 0);
        cam.update();
        font.getData().setScale(FONT_SCALING * width / widthU, FONT_SCALING * height / heightU);
    }
}
