package br.unitins.topicos1.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserFileService implements FileService {
    // /Users/janio/quarkus/images/usuario/
    
    private final String PATH_USER = System.getProperty("user.home") +
        File.separator + "quarkus" +
        File.separator + "images" +
        File.separator + "usuario" +  File.separator;
    
    private static final List<String> SUPPORTED_MIME_TYPES = Arrays.asList("image/jpeg", "image/jpg", "image/png", "image/gif");

    private static final int MAX_FILE_SIZE = 1024 * 1024 * 10; // 10mb 

    @Override
    public String save(String fileName, byte[] file) throws IOException {
        verifyImageSize(file);

        verifyImageType(fileName);

        // criar diretorio caso nao exista
        Path diretorio = Paths.get(PATH_USER);
        Files.createDirectories(diretorio);

        // criando o nome do file randomico
        String mimeType = Files.probeContentType(Paths.get(fileName));
        String extensao = mimeType.substring(mimeType.lastIndexOf('/') + 1);
        String novoFileName = UUID.randomUUID() + "." + extensao;

        // defindo o caminho completo do file
        Path filePath = diretorio.resolve(novoFileName);

        if (filePath.toFile().exists()) 
            throw new IOException("Nome de file ja existe. Os alunos vão buscar uma melhor solucao.");

        // salvar file
        try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
            fos.write(file);
        }

        return filePath.toFile().getName();
    }

    @Override
    public File getFile(String fileName) {
        File file = new File(PATH_USER+fileName);
        return file;
    }

    private void verifyImageSize(byte[] file) throws IOException {
        if (file.length > MAX_FILE_SIZE) 
            throw new IOException("File maior que 10mb.");
    }

    
    private void verifyImageType(String fileName) throws IOException {
        String mimeType = Files.probeContentType(Paths.get(fileName));
        if (mimeType == null || !SUPPORTED_MIME_TYPES.contains(mimeType)) 
            throw new IOException("Tipo de imagem não suportada.");
  
    }


    
}