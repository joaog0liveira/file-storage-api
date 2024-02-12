# file-storage-api

Para fazer o teste de Upload de arquivo crie uma pasta com nome files na pasta Downloads;
Também crie um arquivo dentro da pasta files, no caso o meu era o test.txt.

comando para fazer upload: 
curl -X POST -F "file=@test.txt" http://localhost:8080/api/files/upload
Lembre de alterar o nome do arquivo no comando

comando para download do arquivo:
curl -OJL http://localhost:8080/api/files/download/test.txt -v

o link do download sera gerado quando o upload for feito
 
