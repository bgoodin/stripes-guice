mvn clean deploy site-deploy assembly:single
scp target/stripes-guice-dist* bgoodin@silvermindsoftware.com:/usr/local/resin/hosts/www.silvermindsoftware.com/webapps/ROOT/stripesguice-dist/
