import model.Card;
import model.CardRaw;
import model.CardSimpleProp;
import model.ModelHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class CardJsonWriter {
    private String path;
    private RandomAccessFile file;
    private ModelHelper modelHelper;

    CardJsonWriter(String path) throws IOException {
        this.path = path;
        this.modelHelper = new ModelHelper();
        this.open();
    }

    private void appendString(String str) throws IOException {
        appendString(str, 0);
    }
    private void appendString(String str, int offset) throws IOException {
        byte[] strBytes = str.getBytes("UTF-8");
        file.getChannel().map(
                FileChannel.MapMode.READ_WRITE,
                file.length() + offset, strBytes.length)
                .put(strBytes);
    }

    private void appendBytes(byte[] bytes) throws IOException {
        file.getChannel().map(
                FileChannel.MapMode.READ_WRITE,
                file.length(), bytes.length)
                .put(bytes);
    }

    public void append(Card card) throws IOException {
        JSONObject obj = new JSONObject();

        obj.put("idNumeric", card.idNumeric);
        obj.put("idStr", card.idStr);
        obj.put("name", new JSONObject().put("en", card.name.en).put("de", card.name.de));

        obj.put("editionId", card.editionId);

        if (card.typeIds != null) {
            JSONArray type = new JSONArray();
            card.typeIds.forEach(t -> type.put(t));
            obj.put("type", type);
        }

        if (card.raceIds != null) {
            JSONArray race = new JSONArray();
            card.raceIds.forEach(t -> race.put(t));
            obj.put("raceIds", race);
        }

        if (card.attributeIds != null) {
            JSONArray attribute = new JSONArray();
            card.attributeIds.forEach(t -> attribute.put(t));
            obj.put("attributeIds", attribute);
        }

        if (card.ability != null) {
            JSONArray ability = new JSONArray();
            card.ability.forEach(t -> {
                JSONObject abilityObj = new JSONObject();
                abilityObj.put("type", t.typeId);
                if (t.value != null) {
                    abilityObj.put("value", new JSONObject().put("en", t.value.en).put("de", t.value.de));
                }

                if (t.cost != null) {
                    JSONArray cost = new JSONArray();
                    t.cost.forEach(c -> {
                        JSONObject costObj = new JSONObject();
                        costObj.put("type", c.typeId);
                        costObj.put("count", c.count);
                        cost.put(costObj);
                    });
                    abilityObj.put("cost", cost);
                }

                ability.put(abilityObj);
            });
            obj.put("ability", ability);
        }

        if (card.cost != null) {
            JSONArray cost = new JSONArray();
            card.cost.forEach(c -> {
                JSONObject costObj = new JSONObject();
                costObj.put("type", c.typeId);
                costObj.put("count", c.count);
                cost.put(costObj);
            });
            obj.put("cost", cost);
        }

        if (card.flavor != null) {
            obj.put("flavor", new JSONObject().put("en", card.flavor.en).put("de", card.flavor.de));
        }

        obj.put("rarity", card.rarity);
        obj.put("atk", card.atk);
        obj.put("def", card.def);
        obj.put("imageSrcUrl", card.imageSrcUrl);

        appendString(obj.toString(1));

        appendString(",\n");
    }

    public void append(CardRaw cardRaw) throws IOException {
        append(modelHelper.processCardRaw(cardRaw));
        /*
        JSONObject obj = new JSONObject();

        obj.put("idNumeric", cardRaw.idNumeric);
        obj.put("idStr", cardRaw.idStr);
        obj.put("name", new JSONObject().put("en", cardRaw.name.en).put("de", cardRaw.name.de));
        obj.put("editionId", new JSONObject().put("en", cardRaw.editionId.en).put("de", cardRaw.editionId.de));
        obj.put("typeIds", new JSONObject().put("en", cardRaw.typeIds.en).put("de", cardRaw.typeIds.de));
        obj.put("raceIds", new JSONObject().put("en", cardRaw.raceIds.en).put("de", cardRaw.raceIds.de));
        obj.put("attributeIds", new JSONObject().put("en", cardRaw.attributeIds.en).put("de", cardRaw.attributeIds.de));
        obj.put("ability", new JSONObject().put("en", cardRaw.ability.en).put("de", cardRaw.ability.de));
        obj.put("flavor", new JSONObject().put("en", cardRaw.flavor.en).put("de", cardRaw.flavor.de));
        obj.put("rarity", cardRaw.rarity);
        obj.put("cost", cardRaw.cost);
        obj.put("atk", cardRaw.atk);
        obj.put("def", cardRaw.def);
        obj.put("imageSrcUrl", cardRaw.imageSrcUrl);

        appendString(obj.toString(1));

        appendString(",\n");*/
    }

    public void close() throws IOException {
        appendString("],\n", -2);

        System.out.println("  Insert CardTypes:");
        JSONArray cardTypes = new JSONArray();
        for (CardSimpleProp cardSimpleProp : modelHelper.cardTypes) {
            System.out.println("   [" + cardSimpleProp.id + "]: \"" +
                    cardSimpleProp.value.de + "\" | \"" +
                    cardSimpleProp.value.en + "\"");

            cardTypes.put(new JSONObject()
                    .put("id", cardSimpleProp.id)
                    .put("value", new JSONObject()
                        .put("en", cardSimpleProp.value.en)
                        .put("de", cardSimpleProp.value.de)));
        }
        appendString("\"cardTypes\": " + cardTypes.toString(1) + ",\n");

        System.out.println("  Insert CardEdition:");
        JSONArray cardEdition = new JSONArray();
        for (CardSimpleProp cardSimpleProp : modelHelper.cardEdition) {
            System.out.println("   [" + cardSimpleProp.id + "]: \"" +
                    cardSimpleProp.value.de + "\" | \"" +
                        cardSimpleProp.value.en + "\"");

            cardEdition.put(new JSONObject()
                    .put("id", cardSimpleProp.id)
                    .put("value", new JSONObject()
                            .put("en", cardSimpleProp.value.en)
                            .put("de", cardSimpleProp.value.de)));
        }
        appendString("\"cardEdition\": " + cardEdition.toString(1) + ",\n");


        System.out.println("  Insert CardAttributes:");
        JSONArray cardAttributes = new JSONArray();
        for (CardSimpleProp cardSimpleProp : modelHelper.cardAttribute) {
            System.out.println("   [" + cardSimpleProp.id + "]: \"" +
                    cardSimpleProp.value.de + "\" | \"" +
                    cardSimpleProp.value.en + "\"");

            cardAttributes.put(new JSONObject()
                    .put("id", cardSimpleProp.id)
                    .put("value", new JSONObject()
                            .put("en", cardSimpleProp.value.en)
                            .put("de", cardSimpleProp.value.de)));
        }
        appendString("\"cardAttributes\": " + cardAttributes.toString(1) + ",\n");


        System.out.println("  Insert CardRace:");
        JSONArray cardRace = new JSONArray();
        for (CardSimpleProp cardSimpleProp : modelHelper.cardRace) {
            System.out.println("   [" + cardSimpleProp.id + "]: \"" +
                    cardSimpleProp.value.de + "\" | \"" +
                    cardSimpleProp.value.en + "\"");

            cardRace.put(new JSONObject()
                    .put("id", cardSimpleProp.id)
                    .put("value", new JSONObject()
                            .put("en", cardSimpleProp.value.en)
                            .put("de", cardSimpleProp.value.de)));
        }
        appendString("\"cardRace\": " + cardRace.toString(1) + ",\n");


        System.out.println("  Insert CardAbilityType:");
        JSONArray cardAbilityType = new JSONArray();
        for (CardSimpleProp cardSimpleProp : modelHelper.cardAbilityType) {
            System.out.println("   [" + cardSimpleProp.id + "]: \"" +
                    cardSimpleProp.value.de + "\" | \"" +
                    cardSimpleProp.value.en + "\"");

            cardAbilityType.put(new JSONObject()
                    .put("id", cardSimpleProp.id)
                    .put("value", new JSONObject()
                            .put("en", cardSimpleProp.value.en)
                            .put("de", cardSimpleProp.value.de)));
        }
        appendString("\"cardAbilityType\": " + cardAbilityType.toString(1) + "\n}");

        file.close();
    }

    public void open() throws IOException {
        file = new RandomAccessFile(this.path, "rw");
        appendString("{\n \"cards\": [\n");
    }
}
