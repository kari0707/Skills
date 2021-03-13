package com.kari.skills.skill;

import com.kari.skills.Main;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FilenameFilter;
import java.util.*;

public class Skills {
    private static final FilenameFilter FILENAME_FILTER = (File dir, String name) -> name.startsWith("Skills-") && name.endsWith(".yml");
    private final List<File> Files = new ArrayList<>();
    private static final HashMap<String, List<Skill>> skills = new HashMap<>(); //job, Skills

    public Skills(Main plugin) {
        File folder = plugin.getDataFolder();
        Files.addAll(Arrays.asList(folder.listFiles(FILENAME_FILTER)));
        YamlConfiguration yml = new YamlConfiguration();
        Variables variables = Variables.getInstance();
        variables.UPDATEDATA.clear();
        String filename = null;
        try {
            for (File file : Files) {//파일 값
                filename = file.getName();
                yml.load(file);
                Set<String> keys = yml.getKeys(false);
                String job = replaceJobName(file.getName());
                List<Skill> skills = new ArrayList<>();
                for(String key : keys) { //키 값
                    ConfigurationSection section = yml.getConfigurationSection(key);
                    variables.UPDATEDATA.put(key, section);
                    if(section == null) continue;
                    Skill skill = new Skill(section);
                    skills.add(skill);
                }
                Skills.skills.put(job, skills);
            }
        } catch (Exception exception) {
            System.out.println(filename + "스킬 파일 로드중 에러 발생: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    public HashMap<String, List<Skill>> getSkills() {
        return skills;
    }

    public List<File> getFiles() {
        return Files;
    }

    private String replaceJobName(String name) {
        return name.replaceAll("Skills-", "").replaceAll("\\.yml", "");
    }
}
