package com.island.aadhar.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class CharacterSetReader {
    private static List<String> characterList = new ArrayList<>();

    static{
        try {
            populateCharacterList();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Exception in reading characterSet.csv");
        }
    }

    private static void populateCharacterList() throws IOException {
        InputStream inputStream = null;
        InputStreamReader streamReader = null;
        BufferedReader reader = null;
        try {
            ClassPathResource resource = new ClassPathResource("characterSet.csv");
            inputStream = resource.getInputStream();
            streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            reader = new BufferedReader(streamReader);
            for (String characterString; (characterString = reader.readLine()) != null;) {
                String[] characters = characterString.split(",");
                characterList = Arrays.asList(characters);
            }
        }
        catch(IOException e)
        {
            log.error("Couldn't read character set", e);
            throw e;
        }
        finally {
            if(reader != null ) {reader.close();}
            if(streamReader != null ) {streamReader.close();}
            if(inputStream != null ) {inputStream.close();}
        }
    }

    public static List<String> getCharacterList(){
        return characterList;
    }
}
