mvn clean package deploy site-deploy assembly:assembly
scp target/stripes-guice-dist* bgoodin@silvermindsoftware.com:/usr/local/resin/hosts/www.silvermindsoftware.com/webapps/ROOT/stripesguice-dist/
