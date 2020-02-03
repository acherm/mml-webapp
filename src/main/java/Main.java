import org.xtext.example.mydsl.generator.MMLLoader;
import org.xtext.example.mydsl.mml.MMLModel;

import io.javalin.Javalin;
import io.javalin.core.util.FileUtil;

public class Main {

  
    public static void main(String[] args) {

        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/public");
        }).start(8080);

        app.get("/generate", ctx -> {
            ctx.html("Your MML " + processMML(ctx.queryParam("mml")) + " will be processed");
        });

        app.post("/upload-example", ctx -> {
            ctx.uploadedFiles("files").forEach(file -> {
                FileUtil.streamToFile(file.getContent(), "upload/" + file.getFilename());
            });
            ctx.html("Upload successful");
        });

    }

	private static String processMML(String mmlContent) {
		MMLLoader mmlLoader = new MMLLoader();	    
		MMLModel mml = mmlLoader.loadModel(mmlContent);
		return mml.getInput().toString();
	}

}


