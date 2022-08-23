package command.commands.UtilityCommands;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import command.ICommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.FileUpload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.Map;

public class qrCodeCommand implements ICommand {
    @Override
    public void handle(SlashCommandInteractionEvent event) throws IOException {
        //Generate a QrCode by the given URl

        //Get the URL from the event
        String url = event.getOption("url").getAsString();
        //Generate the QrCode

        try {
            event.replyFiles(FileUpload.fromData(getQRCodeImage(url, 200, 200),"test.jpg")).queue();
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public String getName() {
        return "qrcode";
    }

    @Override
    public String getHelp() {
        return "Generates a QR code for the given text";
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash(this.getName(), this.getHelp())
                .addOptions(new OptionData(OptionType.STRING,"url","Specify the Url for the QR Code", true)
        );
    }
    public static byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.MARGIN, 2); /* default = 4 */


        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageConfig con = new MatrixToImageConfig() ;

        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream,con);

        return pngOutputStream.toByteArray();

    }
}
