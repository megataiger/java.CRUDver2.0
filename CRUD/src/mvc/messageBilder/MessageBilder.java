package mvc.messageBilder;

import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@Service
public class MessageBilder {
    public JsonObject createErrorMessage(BindingResult result) {
        JsonObject message = new JsonObject();
        StringBuilder string = new StringBuilder();
        for(ObjectError e : result.getAllErrors()) {
            string.append(e.getDefaultMessage());
            string.append("\n");
        }
        message.addProperty("response", false);
        message.addProperty("message", string.toString());
        return message;
    }

    public JsonObject createSuccessMessage() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("response", true);
        jsonObject.addProperty("message", "Успешно!");
        return jsonObject;
    }
}