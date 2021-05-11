
# Cuidar API Spring Boot

## Repositório

```
# Clonando o repositório da API Spring Boot
git clone https://github.com/prezasoftwares/cuidar-app
```

## Banco de dados (Postgres)
```
# Acessar diretório com docker compose file
cd infra/

# Subir container com Postgres
docker-compose up -d
```
## Variáveis de ambiente
```
#Defina no sistema as variáveis de ambiente:
-user var
spring.profiles.active = dev

-system var
cuidarUserRegisterSecret = $secret$
```

## Executar a aplicação
```
# Voltar para o diretório raíz da aplicação
cd ..

# Rodando a aplicação
mvn spring-boot:run

```
