package ca.canadien3005.artefact.screen;

import ca.canadien3005.artefact.Artefact;
import ca.canadien3005.artefact.item.custom.MultiToolsItem;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class MultiToolsScreen extends Screen {

    protected static final ResourceLocation GUI_TEXTURE =
            new ResourceLocation(Artefact.MOD_ID, "textures/gui/multi_tools.png");

    protected static final ResourceLocation BUTTON_TEXTURE =
            new ResourceLocation(Artefact.MOD_ID, "textures/gui/small_button_arrow.png");

    private final int xSize = 172;
    private final int ySize = 172;
    private int guiY;
    private int guiX;
    private static ItemStack itemStack;
    private static int page;
    private static final int numberOfPages = (int) Math.ceil(MultiToolsItem.items.size()/8f);
    private static int[][] coordinate;
    private static boolean shouldClose = false;
    private static int indice = -2;

    public ImageButton nextPageButton;
    public ImageButton prevPageButton;
    public ImageButton imageButton;
    public ImageButton[] imageButtons = new ImageButton[8];

    // Constructeur
    public MultiToolsScreen(ItemStack itemStack) {
        super(new TranslatableComponent("gui." + Artefact.MOD_ID + ".multi_tools"));
        page = 1;
        MultiToolsScreen.itemStack = itemStack;
    }

    public static void setIndice(int i) {
        indice = i;
    }

    @Override
    protected void init() {
        // Initialisation des éléments du GUI (boutons, champs de texte, etc.)
        this.guiX = (this.width - this.xSize) /2;
        this.guiY = (this.height - this.ySize) /2;

        imageButton = new ImageButton((this.width-16)/2, (this.height-16)/2, 16, 16, 240, 240, BUTTON_TEXTURE, this::selectedItem);
        nextPageButton = new ImageButton((this.width/2) + 12, (this.height/2) + 15, 7, 12, 10, 5, BUTTON_TEXTURE, this::nextPage);
        prevPageButton = new ImageButton((this.width/2) - 18, (this.height/2) + 15, 7, 12, 23, 5, BUTTON_TEXTURE, this::previousPage);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (shouldClose) {
            shouldClose = false;
            onClose();
        }

        this.removed();
        drawBackground(matrixStack);
        drawItem(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false; // Détermine si le GUI pause le jeu
    }

    private void drawBackground(PoseStack matrixStack) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        // Render the wheel
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        blit(matrixStack, guiX, guiY, 0, 0, 172, 172, this.xSize, this.ySize);

        if (numberOfPages > 1){
            addRenderableWidget(nextPageButton);
            addRenderableWidget(prevPageButton);
            drawCenteredString(matrixStack, this.font, page + "/" + (int) numberOfPages, this.width / 2, (this.height/2) + 17, 16777215);
            // Add arrow to switch page
        }

        //this.blit(matrixStack, x, y, posXTexture, posYTexture, width, height, this.xSize, this.ySize);
    }

    private void drawItem(PoseStack matrixStack){
        int xCenter = (this.width-16)/2;
        int yCenter = (this.height-16)/2;

        //Just the coordinate of the icons in the wheel
        coordinate = new int[][]{
                {xCenter, yCenter - 74},
                {xCenter + 56, yCenter - 57},
                {xCenter + 74, yCenter - 1},
                {xCenter + 56, yCenter + 56},
                {xCenter, yCenter + 74},
                {xCenter - 56, yCenter + 56},
                {xCenter - 74, yCenter - 1},
                {xCenter - 56, yCenter - 57}
        };

        RenderSystem.setShaderTexture(0, getItemTexture(itemStack.getItem()));
        blit(matrixStack, xCenter, yCenter, 0, 0, 16, 16, 16, 16);
        addRenderableWidget(imageButton);

        for (int i = 8*(page-1); i < 8*page && i < MultiToolsItem.items.size(); i++){
            Item item = MultiToolsItem.items.get(i);

            RenderSystem.setShaderTexture(0, getItemTexture(item));
            blit(matrixStack, coordinate[i%8][0], coordinate[i%8][1], 0, 0, 16, 16, 16, 16);
            imageButtons[i%8] = new ImageButton(coordinate[i%8][0], coordinate[i%8][1], 16, 16, 240, 240, BUTTON_TEXTURE, this::selectedItem);

            addRenderableWidget(imageButtons[i%8]);
        }

    }

    private ResourceLocation getItemTexture(Item item) {
        // Get where the icon is
        ResourceLocation resourceLocation = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(item).getParticleIcon().getName();
        // Create a new correct path
        return new ResourceLocation(resourceLocation.getNamespace(), "textures/" + resourceLocation.getPath() + ".png");
    }

    private void nextPage(Button button){
        if (page < numberOfPages) {
            page++;
        }
    }

    private void previousPage(Button button){
        if (page > 1) {
            page--;
        }
    }

    private void selectedItem(Button button){
        int x = button.x;
        int y = button.y;
        int itemIndice = -1;

        for (int i = 0; i < coordinate.length; i++) {
            if (coordinate[i][0] == x && coordinate[i][1] == y){
                itemIndice = i;
            }
        }

        if (itemIndice != -1){
            indice = itemIndice+((page-1)*8);
        } else {
            if (coordinate[0][0] == x && coordinate[2][1]+1 == y){
                indice = -1;
            }
        }

        shouldClose = true;
    }

    public static int getIndice() {
        return indice;
    }

    @Override
    public void removed() {
        this.removeWidget(imageButton);
        this.removeWidget(prevPageButton);
        this.removeWidget(nextPageButton);

        for (ImageButton button : imageButtons) {
            this.removeWidget(button);
        }


        super.removed();
    }
}
