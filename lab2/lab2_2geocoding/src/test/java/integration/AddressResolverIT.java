package integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import connection.TqsBasicHttpClient;
import geocoding.Address;
import geocoding.AddressResolver;


public class AddressResolverIT {

    private TqsBasicHttpClient tqsBasicHttpClient;

    private AddressResolver addressResolver;

    @BeforeEach
    public void init(){
        tqsBasicHttpClient = new TqsBasicHttpClient();
        addressResolver = new AddressResolver(tqsBasicHttpClient);
    }

    @Test
    public void whenGoodCoordidates_returnAddress() throws IOException, URISyntaxException, ParseException {

        //todo

        // repeat the same tests conditions from AddressResolverTest, without mocks
        Optional<Address> result = addressResolver.findAddressForLocation( 40.633116,-8.658784);

        Address expected = new Address( "Avenida João Jacinto de Magalhães", "Aveiro", "", "3810-149", null);

        assertTrue( result.isPresent());
        assertEquals( expected, result.get());

    }

    @Test
    public void whenBadCoordidates_thenReturnNoValidAddrress() throws IOException, URISyntaxException, ParseException {

        //todo
        // repeat the same tests conditions from AddressResolverTest, without mocks
        assertThrows(IllegalArgumentException.class, () -> addressResolver.findAddressForLocation( -361,-361));
    }

}