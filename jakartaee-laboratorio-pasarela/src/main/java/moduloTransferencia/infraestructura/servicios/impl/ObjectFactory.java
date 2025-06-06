
package moduloTransferencia.infraestructura.servicios.impl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the moduloTransferencia.infraestructura.servicios.impl package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _RealizarTransferencia_QNAME = new QName("http://impl.servicios.infraestructura.moduloTransferencia/", "Realizar_Transferencia");
    private final static QName _RealizarTransferenciaResponse_QNAME = new QName("http://impl.servicios.infraestructura.moduloTransferencia/", "Realizar_TransferenciaResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: moduloTransferencia.infraestructura.servicios.impl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RealizarTransferencia }
     * 
     */
    public RealizarTransferencia createRealizarTransferencia() {
        return new RealizarTransferencia();
    }

    /**
     * Create an instance of {@link RealizarTransferenciaResponse }
     * 
     */
    public RealizarTransferenciaResponse createRealizarTransferenciaResponse() {
        return new RealizarTransferenciaResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RealizarTransferencia }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RealizarTransferencia }{@code >}
     */
    @XmlElementDecl(namespace = "http://impl.servicios.infraestructura.moduloTransferencia/", name = "Realizar_Transferencia")
    public JAXBElement<RealizarTransferencia> createRealizarTransferencia(RealizarTransferencia value) {
        return new JAXBElement<RealizarTransferencia>(_RealizarTransferencia_QNAME, RealizarTransferencia.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RealizarTransferenciaResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RealizarTransferenciaResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://impl.servicios.infraestructura.moduloTransferencia/", name = "Realizar_TransferenciaResponse")
    public JAXBElement<RealizarTransferenciaResponse> createRealizarTransferenciaResponse(RealizarTransferenciaResponse value) {
        return new JAXBElement<RealizarTransferenciaResponse>(_RealizarTransferenciaResponse_QNAME, RealizarTransferenciaResponse.class, null, value);
    }

}
