
package moduloCompra.infraestructura.servicios.Medio_De_Pago;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the moduloCompra.infraestructura.servicios.Medio_De_Pago package. 
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

    private final static QName _RealizarCompra_QNAME = new QName("http://servicios.infraestructura.moduloCompra/", "Realizar_Compra");
    private final static QName _RealizarCompraResponse_QNAME = new QName("http://servicios.infraestructura.moduloCompra/", "Realizar_CompraResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: moduloCompra.infraestructura.servicios.Medio_De_Pago
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RealizarCompra }
     * 
     */
    public RealizarCompra createRealizarCompra() {
        return new RealizarCompra();
    }

    /**
     * Create an instance of {@link RealizarCompraResponse }
     * 
     */
    public RealizarCompraResponse createRealizarCompraResponse() {
        return new RealizarCompraResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RealizarCompra }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RealizarCompra }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicios.infraestructura.moduloCompra/", name = "Realizar_Compra")
    public JAXBElement<RealizarCompra> createRealizarCompra(RealizarCompra value) {
        return new JAXBElement<RealizarCompra>(_RealizarCompra_QNAME, RealizarCompra.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RealizarCompraResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RealizarCompraResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicios.infraestructura.moduloCompra/", name = "Realizar_CompraResponse")
    public JAXBElement<RealizarCompraResponse> createRealizarCompraResponse(RealizarCompraResponse value) {
        return new JAXBElement<RealizarCompraResponse>(_RealizarCompraResponse_QNAME, RealizarCompraResponse.class, null, value);
    }

}
