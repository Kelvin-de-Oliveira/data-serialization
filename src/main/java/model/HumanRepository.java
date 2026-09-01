package model;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import com.j256.ormlite.table.TableUtils;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class HumanRepository
{
    private static Database database;
    private static Dao<Human, Integer> dao;
    private List<Human> loadedHumans;
    private Human loadedHuman;

    public HumanRepository(Database database) {
        HumanRepository.setDatabase(database);
        loadedHumans = new ArrayList<Human>();
    }

    public static void setDatabase(Database database) {
        HumanRepository.database = database;
        try {
            dao = DaoManager.createDao(database.getConnection(), Human.class);
            TableUtils.createTableIfNotExists(database.getConnection(), Human.class);
        }
        catch(SQLException e) {
            System.out.println(e);
        }
    }

    public Human create(Human student) {
        int nrows = 0;
        try {
            nrows = dao.create(student);
            if ( nrows == 0 )
                throw new SQLException("Error: object not saved");
            this.loadedHuman = student;
            loadedHumans.add(student);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return student;
    }

    public void update(Human student) {
        try {
            dao.update(student);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void delete(Human student) {
        try {
            dao.delete(student);
            this.loadedHumans.remove(student);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public Human loadFromId(int id) {
        try {
            this.loadedHuman = dao.queryForId(id);
            if (this.loadedHuman != null)
                this.loadedHumans.add(this.loadedHuman);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return this.loadedHuman;
    }

    public List<Human> loadAll() {
        try {
            this.loadedHumans =  dao.queryForAll();
            if (!this.loadedHumans.isEmpty())
                this.loadedHuman = this.loadedHumans.get(0);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return this.loadedHumans;
    }

    /* data serialization */
    public String dumpData(String formato) {
        List<Human> humans = loadAll();
        try {
            if (formato.equalsIgnoreCase("json")) {
                HumanJsonSerializer s = new HumanJsonSerializer();
                return s.toJson(humans);
            } else if (formato.equalsIgnoreCase("xml")) {
                HumanXmlSerializer s = new HumanXmlSerializer();
                return s.toXml(humans);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean dumpFile(String formato, File arquivo) {
        String data = dumpData(formato);
        if (data == null) return false;
        try (FileWriter writer = new FileWriter(arquivo)) {
            writer.write(data);
            return true;
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
    }

    public Human createFromJSON(String json) {
        HumanJsonSerializer s = new HumanJsonSerializer();
        Human human = s.fromJson(json);
        human.setId(0);
        return create(human);
    }

    public Human createFromXML(String xml) {
        try {
            HumanXmlSerializer s = new HumanXmlSerializer();
            Human human = s.fromXml(xml);
            human.setId(0);
            return create(human);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public int importData(String formato, String data) {
        int count = 0;
        try {
            List<Human> humans;
            if (formato.equalsIgnoreCase("json")) {
                HumanJsonSerializer s = new HumanJsonSerializer();
                humans = s.listFromJson(data);
            } else if (formato.equalsIgnoreCase("xml")) {
                HumanXmlSerializer s = new HumanXmlSerializer();
                humans = s.listFromXml(data);
            } else {
                return 0;
            }
            for (Human h : humans) {
                h.setId(0);
                create(h);
                count++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return count;
    }

    public int importFile(String formato, File arquivo) {
        try {
            String data = new String(Files.readAllBytes(arquivo.toPath()));
            return importData(formato, data);
        } catch (IOException e) {
            System.out.println(e);
            return 0;
        }
    }



}