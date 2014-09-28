package com.example.mobile;

import java.util.ArrayList;
import java.util.List;

import com.example.mobile.Manufacturers.Manufacturer;
import com.example.mobile.part.Part;



/**
 *
 * @author vrg
 */
public class Mobile {
    private Manufacturer manufacturer;
    private String model;
    private List<Part> parts;
    private String otherInfo;

    
    private Mobile() {
        parts = new ArrayList<>();
    }
    
    public static MobileBuilder getBuilder() {
        return new MobileBuilder();
    }
    
    public List<Part> getParts() {
        return parts;
    }

    @Override
    public String toString() {
        return manufacturer + " " + model + ", other Info: " + otherInfo;
    }

    
    public static class MobileBuilder {
        private Mobile mobile;
        
        private MobileBuilder() {
            mobile = new Mobile();
        }
        
        public MobileBuilder manufacturer(Manufacturer manufacturer) {
            mobile.manufacturer = manufacturer;
            return this;
        }
        
        public MobileBuilder model(String model) {
            mobile.model = model;
            return this;
        }
        
        public MobileBuilder orherInfor(String otherInfo) {
            mobile.otherInfo = otherInfo;
            return this;
        }
        
        public MobileBuilder addPart(Part part) {
            mobile.parts.add(part);
            return this;
        }
        
        public Mobile build() {
            return this.mobile;
        }
    }

}
