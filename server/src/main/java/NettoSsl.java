import java.security.cert.CertificateException;

import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.nettosphere.Config;
import org.atmosphere.nettosphere.Handler;
import org.atmosphere.nettosphere.Nettosphere;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

public class NettoSsl implements Handler {

  Nettosphere nettosphere = null;

  public NettoSsl() throws CertificateException, Exception {
    Config.Builder configBuilder = new Config.Builder();
    configBuilder.port(8080);

    boolean ssl = false;
    if (ssl) {
      SelfSignedCertificate ssc = new SelfSignedCertificate("localhost");
      SslContext sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey(), null).build();
      configBuilder.sslContext(sslCtx);
    }

    configBuilder.resource("/api", this);
    configBuilder.resource("./");
    nettosphere = new Nettosphere.Builder().config(configBuilder.build()).build();
    nettosphere.start();

  }

  public static void main(String[] args) {
    try {
      
      new NettoSsl();

    } catch (Exception e) {
      System.out.println("main threw");
      e.printStackTrace();
    }

  }

  @Override
  public void handle(AtmosphereResource r) {
    // nettosphere.framework().getBroadcasterFactory();
    r.suspend();

  }

}
