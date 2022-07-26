import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

public class StickerFactory {
    
    /**
     * @throws Exception
     */
    public void create(InputStream stream, String fileName, String message) throws Exception {

        // leitura da imagem        
        BufferedImage image = ImageIO.read(stream);        

        // cria nova imagem, ajustando tamanho e com transparência
        int stickerHeight = image.getHeight() + 100;
        BufferedImage stickerImage = new BufferedImage(image.getWidth(), stickerHeight, BufferedImage.TRANSLUCENT);

        // copiar a imagem original na nova (em memória)
        Graphics2D graphics = (Graphics2D) stickerImage.getGraphics();
        graphics.drawImage(image, 0, 0, null);

        // configurar a fonte
        var font = new Font("Impact", Font.BOLD, 32);
        graphics.setFont(font);
        graphics.setColor(Color.YELLOW);

        // escrever uma frase na nova imagem e centraliza
        if (!message.isEmpty()) {

            FontMetrics metrics = graphics.getFontMetrics();
            int centralX = (stickerImage.getWidth() - metrics.stringWidth(message)) / 2;        
            graphics.drawString(message, centralX, stickerHeight - 40);            
        }

        var fs = FileSystems.getDefault();
        Path path = fs.getPath("output");    
        if (Files.notExists(path)) Files.createDirectory(path);        
        Path stickerPath = path.resolve(fs.getPath(fileName.replaceAll(":", "")));

        // escrever a nova imagem para um arquivo
        ImageIO.write(stickerImage, "png", stickerPath.toFile());
    }
}
