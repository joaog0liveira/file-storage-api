# file-storage-api
Projeto de estudos desenvolvido em Java com Spring Boot. Ele permite fazer upload, download e listar arquivos armazenados em um diretório específico no servidor.

Este projeto foi desenvolvido como parte de um estudo,[seguindo o tutorial disponível em:](https://www.youtube.com/watch?v=b6kvS1Wszew&ab_channel=GiulianaBezerra)


Funcionalidades
Upload de Arquivos: Permite fazer upload de arquivos para o servidor.
Download de Arquivos: Permite baixar arquivos previamente enviados para o servidor.
Listagem de Arquivos: Fornece uma lista dos arquivos armazenados no servidor.
Como Testar
Pré-requisitos
Certifique-se de ter o Java e o Maven instalados no seu sistema.
Você também precisará do curl instalado para executar os comandos de teste.
Passos para Testar
Clone o Repositório:

bash
Copiar código
git clone https://github.com/seu-usuario/file-storage-api.git
Compilar o Projeto:

bash
Copiar código
cd file-storage-api
mvn clean package
Configurar o Diretório de Upload:

Edite o arquivo application.properties dentro do diretório src/main/resources.
Defina o diretório de upload para onde os arquivos serão enviados:
python
Copiar código
file.upload-dir=uploads
Iniciar o Serviço:

bash
Copiar código
java -jar target/file-storage-api.jar
Testar o Upload de Arquivo:

Antes de executar este comando, crie uma pasta chamada files dentro da pasta Downloads do seu sistema e coloque um arquivo dentro dela (por exemplo, test.txt).
bash
Copiar código
curl -X POST -F "file=@caminho/do/arquivo/test.txt" http://localhost:8080/api/files/upload
Testar o Download de Arquivo:

bash
Copiar código
curl -OJL http://localhost:8080/api/files/download/test.txt
Listar Arquivos Disponíveis:

bash
Copiar código
curl http://localhost:8080/api/files/list
Contribuindo
Contribuições são bem-vindas! Sinta-se à vontade para enviar pull requests ou abrir issues se encontrar algum problema.
