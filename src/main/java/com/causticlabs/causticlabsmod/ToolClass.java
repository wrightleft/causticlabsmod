package com.causticlabs.causticlabsmod;

public enum ToolClass {
   PICKAXE("pickaxe"),
   AXE("axe"),
   SHOVEL("shovel");
   
   private final String _value;
   
   private ToolClass(final String value) {
      _value = value;
   }
   
   @Override
   public String toString() {
      return _value;
   }
}
