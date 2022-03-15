# MutalTlsDemo

Es la comunicación bidireccional, en el que tanto cliente como servidor tienen que confiar el uno en el otro. Por lo que en la creación de los certificados ambos tendrán relación. Cuando se hace esta forma de autenticación el servidor presenta un certificado al cliente, y el cliente presenta un certificado al servidor para que ambos se encuentren autenticados.

![image](https://user-images.githubusercontent.com/48995165/158370741-de7286d5-da71-486c-92f0-6f7cdbc1daeb.png)

Este proyecto consta de 2 partes:
1. Server: la entidad que verifica el certificado en doble sentido.
2. Client: la entidad que emite petición hacia el Server junto con el certificado.

## Generación de certificados
default password: changeit
```bash
# Generar archivo de clave de servidor localhost.jks (cabe modificar los datos sobre la empresa)
keytool -genkey -alias localhost -keyalg RSA -keysize 2048 -sigalg SHA256withRSA -storetype JKS -keystore localhost.jks -dname CN=localhost,OU=Test,O=pkslow,L=Baleares,C=ES -validity 365 -storepass changeit -keypass changeit

# Exportar el archivo de certificado del servidor(localhost)
keytool -export -alias localhost -file localhost.cer -keystore localhost.jks

# Generar el archivo de clave del cliente client.jks  (cabe modificar los datos sobre la empresa)
keytool -genkey -alias client -keyalg RSA -keysize 2048 -sigalg SHA256withRSA -storetype JKS -keystore client.jks -dname CN=client,OU=Test,O=pkslow,L=Baleares,C=ES -validity 365 -storepass changeit -keypass changeit

# Exportar el archivo de certificado del cliente
keytool -export -alias client -file client.cer -keystore client.jks

# Importar el certificado del cliente al servidor(localhost)
keytool -import -alias client -file client.cer -keystore localhost.jks

# Importar el certificado del servidor(localhost) al cliente
keytool -import -alias localhost -file localhost.cer -keystore client.jks

```
### Comprobación de KeysStores
```bash
# Compruebar si el servidor tiene su propia clave privada y el certificado del cliente
keytool -list -keystore localhost.jks
```
resultado esperado
```bash
$ keytool -list -keystore localhost.jks
Introduzca la contrase▒a del almac▒n de claves:  changeit
Tipo de Almac▒n de Claves: jks
Proveedor de Almac▒n de Claves: SUN

Su almac▒n de claves contiene 2 entradas

client, 14-mar-2022, trustedCertEntry,
Huella Digital de Certificado (SHA1): 48:22:FF:19:A6:9F:83:E6:FF:B5:AB:6B:A9:F0:51:72:EB:74:B4:7E
localhost, 14-mar-2022, PrivateKeyEntry,
Huella Digital de Certificado (SHA1): E8:FE:94:9A:50:E7:FB:2A:B4:26:3D:1A:A3:6C:34:00:15:AF:60:D0
```

### Exportación de cerficato en otros formatos
```bash
# Conversión a PKCS12
keytool -importkeystore -srckeystore client.jks -destkeystore client.p12 -srcstoretype JKS -deststoretype PKCS12 -srcstorepass changeit -deststorepass changeit -srckeypass changeit -destkeypass changeit -srcalias client -destalias client -noprompt

# Conversión a .pem y .key
winpty openssl pkcs12 -nokeys -in client.p12 -out client.pem
winpty openssl pkcs12 -nocerts -nodes -in client.p12 -out client.key
```

### Pruebas de conxión 
Para comprobar la conexión se usa comandos **curl**
```bash
curl -v -k --cert client.pem --key client.key https://localhost/server/hello
```
