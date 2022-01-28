package com.epam.izh.rd.online.repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;



public class SimpleFileRepository implements FileRepository {
    public long counter=0;
    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директори
     * @return файлов, в том числе скрытых
     */
    @Override
    public long countFilesInDirectory(String path) {

        File dir1=new File ("src/main/resources/"+path);
        File[] files = dir1.listFiles();
        if (files!=null) {
            return files.length;
        }
        else {
            return 0;
        }
    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {
        File dir0=new File ("src/main/resources/"+path);
        if (dir0.isDirectory()) {
            counter++;
            String[] dirs=dir0.list();
            File[] files=new File[dirs.length];
            int i=0;
            for (String element:dirs) {
                files[i]=new File(path+File.pathSeparator+dirs[i]);
                if (files[i].isDirectory()) {
                    counter+=countDirsInDirectory("src/main/resources/"+path+File.pathSeparator+dirs[i]);
                }
                i++;
            }
        }

        return counter;
    }

    /**
     * Метод копирует все файлы с расширением .txt
     *
     * @param from путь откуда
     * @param to   путь куда
     */
    @Override
    public void copyTXTFiles(String from, String to) {
        File dir=new File(from);
        FileFilter filter = file -> !file.isDirectory() && file.getName().endsWith(".txt");
        File[] files=dir.listFiles(filter);
        try {
            int i=0;
            for (File file:files) {
                Files.copy(Path.of(from + File.pathSeparator + Path.of(file.getName())), Path.of(to+ File.pathSeparator + Path.of(file.getName())));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод создает файл на диске с расширением txt
     *
     * @param path путь до нового файла
     * @param name имя файла
     * @return был ли создан файл
     */
    @Override
    public boolean createFile(String path, String name) {
        File file=new File(path+"[/]"+name);
        boolean x=false;
        try {
            x=file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return x;
    }

    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {
        BufferedReader reader=null;
        StringBuilder line=new StringBuilder(500);
        try {
            reader=new BufferedReader(new FileReader("src/main/resources/"+fileName));
            line.append(reader.readLine());
            while (reader.readLine()!=null) {
                line.append(reader.readLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line.toString();
    }
}
