(function (factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', './kotlin-kotlin-stdlib.js'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('./kotlin-kotlin-stdlib.js'));
  else {
    if (typeof globalThis['kotlin-kotlin-stdlib'] === 'undefined') {
      throw new Error("Error loading module 'kotlin-codepoints-parent-kotlin-codepoints'. Its dependency 'kotlin-kotlin-stdlib' was not found. Please, check whether 'kotlin-kotlin-stdlib' is loaded prior to 'kotlin-codepoints-parent-kotlin-codepoints'.");
    }
    globalThis['kotlin-codepoints-parent-kotlin-codepoints'] = factory(typeof globalThis['kotlin-codepoints-parent-kotlin-codepoints'] === 'undefined' ? {} : globalThis['kotlin-codepoints-parent-kotlin-codepoints'], globalThis['kotlin-kotlin-stdlib']);
  }
}(function (_, kotlin_kotlin) {
  'use strict';
  //region block: imports
  var Unit_instance = kotlin_kotlin.$_$.d;
  var numberToChar = kotlin_kotlin.$_$.c2;
  var protoOf = kotlin_kotlin.$_$.e2;
  var initMetadataForObject = kotlin_kotlin.$_$.z1;
  //endregion
  //region block: pre-declaration
  initMetadataForObject(CodePoints, 'CodePoints');
  //endregion
  function appendCodePoint(_this__u8e3s4, codePoint) {
    // Inline function 'kotlin.apply' call
    if (CodePoints_instance.f9(codePoint)) {
      _this__u8e3s4.g6(numberToChar(codePoint));
    } else {
      _this__u8e3s4.g6(CodePoints_instance.d9(codePoint));
      _this__u8e3s4.g6(CodePoints_instance.e9(codePoint));
    }
    return _this__u8e3s4;
  }
  function CodePoints() {
    this.x8_1 = 65536;
    this.y8_1 = 1114111;
    this.z8_1 = 55296;
    this.a9_1 = 56320;
    this.b9_1 = -56613888;
    this.c9_1 = 55232;
  }
  protoOf(CodePoints).f9 = function (codePoint) {
    return (codePoint >>> 16 | 0) === 0;
  };
  protoOf(CodePoints).d9 = function (codePoint) {
    return numberToChar((codePoint >>> 10 | 0) + 55232 | 0);
  };
  protoOf(CodePoints).e9 = function (codePoint) {
    return numberToChar((codePoint & 1023) + 56320 | 0);
  };
  var CodePoints_instance;
  function CodePoints_getInstance() {
    return CodePoints_instance;
  }
  //region block: init
  CodePoints_instance = new CodePoints();
  //endregion
  //region block: exports
  _.$_$ = _.$_$ || {};
  _.$_$.a = appendCodePoint;
  //endregion
  return _;
}));

//# sourceMappingURL=kotlin-codepoints-parent-kotlin-codepoints.js.map
