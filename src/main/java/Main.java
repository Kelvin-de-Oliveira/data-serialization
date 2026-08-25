import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Database database = new Database("human.db");
        HumanRepository repo = new HumanRepository(database);

        // ── Dados iniciais ───────────────────────────────────────
        Human h1 = new Human();
        h1.setFullName("Ana Paula");
        h1.setIncome(3500.50);
        h1.setBirthday(sdf.parse("15/04/1990"));
        repo.create(h1);

        Human h2 = new Human();
        h2.setFullName("Carlos Souza");
        h2.setIncome(7200.00);
        h2.setBirthday(sdf.parse("02/11/1985"));
        repo.create(h2);

        // ════════════════════════════════════════════════════════
        // TESTE 1 — dumpData JSON
        // ════════════════════════════════════════════════════════
        System.out.println("=== TESTE 1: dumpData (JSON) ===");
        String json = repo.dumpData("json");
        System.out.println(json);

        // ════════════════════════════════════════════════════════
        // TESTE 2 — dumpData XML
        // ════════════════════════════════════════════════════════
        System.out.println("=== TESTE 2: dumpData (XML) ===");
        String xml = repo.dumpData("xml");
        System.out.println(xml);

        // ════════════════════════════════════════════════════════
        // TESTE 3 — dumpFile JSON
        // ════════════════════════════════════════════════════════
        System.out.println("=== TESTE 3: dumpFile (JSON) ===");
        File jsonFile = new File("humans.json");
        boolean okJson = repo.dumpFile("json", jsonFile);
        System.out.println("Arquivo gerado: " + okJson);
        System.out.println("Caminho: " + jsonFile.getAbsolutePath());

        // ════════════════════════════════════════════════════════
        // TESTE 4 — dumpFile XML
        // ════════════════════════════════════════════════════════
        System.out.println("\n=== TESTE 4: dumpFile (XML) ===");
        File xmlFile = new File("humans.xml");
        boolean okXml = repo.dumpFile("xml", xmlFile);
        System.out.println("Arquivo gerado: " + okXml);
        System.out.println("Caminho: " + xmlFile.getAbsolutePath());

        // ════════════════════════════════════════════════════════
        // TESTE 5 — createFromJSON
        // ════════════════════════════════════════════════════════
        System.out.println("\n=== TESTE 5: createFromJSON ===");
        String jsonInput = "{ \"fullName\": \"Julia Mendes\", \"income\": 5100.0, \"birthday\": \"1995-08-20\" }";
        Human fromJson = repo.createFromJSON(jsonInput);
        System.out.println("Criado via JSON: " + fromJson.getFullName() + " | ID: " + fromJson.getId());

        // ════════════════════════════════════════════════════════
        // TESTE 6 — createFromXML
        // ════════════════════════════════════════════════════════
        System.out.println("\n=== TESTE 6: createFromXML ===");
        String xmlInput = """
                <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                <human>
                    <fullName>Roberto Lima</fullName>
                    <income>4300.0</income>
                    <birthday>1988-03-10T00:00:00</birthday>
                </human>
                """;
        Human fromXml = repo.createFromXML(xmlInput);
        System.out.println("Criado via XML: " + fromXml.getFullName() + " | ID: " + fromXml.getId());

        // ════════════════════════════════════════════════════════
        // TESTE 7 — importData JSON
        // ════════════════════════════════════════════════════════
        System.out.println("\n=== TESTE 7: importData (JSON) ===");
        String jsonLista = """
                [
                  { "fullName": "Fernanda Costa", "income": 2900.0, "birthday": "2000-01-15" },
                  { "fullName": "Paulo Salave", "income": 6100.0, "birthday": "1979-07-22" }
                ]
                """;
        int importadosJson = repo.importData("json", jsonLista);
        System.out.println("Objetos importados via JSON: " + importadosJson);

        // ════════════════════════════════════════════════════════
        // TESTE 8 — importFile XML (usa o arquivo gerado no teste 4)
        // ════════════════════════════════════════════════════════
        System.out.println("\n=== TESTE 8: importFile (XML) ===");
        int importadosXml = repo.importFile("xml", xmlFile);
        System.out.println("Objetos importados via arquivo XML: " + importadosXml);

        // ════════════════════════════════════════════════════════
        // VERIFICAÇÃO FINAL — todos os registros no banco
        // ════════════════════════════════════════════════════════
        System.out.println("\n=== VERIFICAÇÃO FINAL: todos os registros ===");
        List<Human> todos = repo.loadAll();
        for (Human h : todos) {
            System.out.println("ID: " + h.getId()
                    + " | Nome: " + h.getFullName()
                    + " | Renda: " + h.getIncome()
                    + " | Nascimento: " + h.printBirthday());
        }

        database.close();
    }
}