(function (factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', './kotlin-kotlin-stdlib.js', './kotlin-codepoints-parent-kotlin-codepoints.js'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('./kotlin-kotlin-stdlib.js'), require('./kotlin-codepoints-parent-kotlin-codepoints.js'));
  else {
    if (typeof globalThis['kotlin-kotlin-stdlib'] === 'undefined') {
      throw new Error("Error loading module 'com.dzikoysk:khangul'. Its dependency 'kotlin-kotlin-stdlib' was not found. Please, check whether 'kotlin-kotlin-stdlib' is loaded prior to 'com.dzikoysk:khangul'.");
    }
    if (typeof globalThis['kotlin-codepoints-parent-kotlin-codepoints'] === 'undefined') {
      throw new Error("Error loading module 'com.dzikoysk:khangul'. Its dependency 'kotlin-codepoints-parent-kotlin-codepoints' was not found. Please, check whether 'kotlin-codepoints-parent-kotlin-codepoints' is loaded prior to 'com.dzikoysk:khangul'.");
    }
    globalThis['com.dzikoysk:khangul'] = factory(typeof globalThis['com.dzikoysk:khangul'] === 'undefined' ? {} : globalThis['com.dzikoysk:khangul'], globalThis['kotlin-kotlin-stdlib'], globalThis['kotlin-codepoints-parent-kotlin-codepoints']);
  }
}(function (_, kotlin_kotlin, kotlin_kotlin_codepoints_parent_kotlin_codepoints) {
  'use strict';
  //region block: imports
  var imul = Math.imul;
  var protoOf = kotlin_kotlin.$_$.e2;
  var Unit_instance = kotlin_kotlin.$_$.d;
  var Default_getInstance = kotlin_kotlin.$_$.c;
  var random = kotlin_kotlin.$_$.f1;
  var defineProp = kotlin_kotlin.$_$.s1;
  var initMetadataForObject = kotlin_kotlin.$_$.z1;
  var VOID = kotlin_kotlin.$_$.b;
  var THROW_IAE = kotlin_kotlin.$_$.s2;
  var Enum = kotlin_kotlin.$_$.r2;
  var initMetadataForClass = kotlin_kotlin.$_$.y1;
  var substring = kotlin_kotlin.$_$.o2;
  var substring_0 = kotlin_kotlin.$_$.n2;
  var charSequenceLength = kotlin_kotlin.$_$.q1;
  var charCodeAt = kotlin_kotlin.$_$.o1;
  var Char__toInt_impl_vasixd = kotlin_kotlin.$_$.m;
  var dropLast = kotlin_kotlin.$_$.k2;
  var charSequenceGet = kotlin_kotlin.$_$.p1;
  var toString = kotlin_kotlin.$_$.n;
  var noWhenBranchMatchedException = kotlin_kotlin.$_$.u2;
  var _Char___init__impl__6a9atx = kotlin_kotlin.$_$.l;
  var Char = kotlin_kotlin.$_$.p2;
  var ArrayList_init_$Create$ = kotlin_kotlin.$_$.e;
  var copyToArray = kotlin_kotlin.$_$.q;
  var split = kotlin_kotlin.$_$.m2;
  var charArrayOf = kotlin_kotlin.$_$.n1;
  var toString_0 = kotlin_kotlin.$_$.f2;
  var IllegalArgumentException_init_$Create$ = kotlin_kotlin.$_$.j;
  var indexOf = kotlin_kotlin.$_$.w;
  var numberRangeToNumber = kotlin_kotlin.$_$.b2;
  var numberToInt = kotlin_kotlin.$_$.d2;
  var primitiveArrayConcat = kotlin_kotlin.$_$.a;
  var until = kotlin_kotlin.$_$.j2;
  var slice = kotlin_kotlin.$_$.l2;
  var StringBuilder_init_$Create$ = kotlin_kotlin.$_$.h;
  var appendCodePoint = kotlin_kotlin_codepoints_parent_kotlin_codepoints.$_$.a;
  var toIntArray = kotlin_kotlin.$_$.h1;
  var getStringHashCode = kotlin_kotlin.$_$.w1;
  var listOf = kotlin_kotlin.$_$.b1;
  var plus = kotlin_kotlin.$_$.e1;
  var collectionSizeOrDefault = kotlin_kotlin.$_$.p;
  var first = kotlin_kotlin.$_$.u;
  var last = kotlin_kotlin.$_$.z;
  var coerceIn = kotlin_kotlin.$_$.i2;
  var NoSuchElementException_init_$Create$ = kotlin_kotlin.$_$.k;
  var compareTo = kotlin_kotlin.$_$.r1;
  var get_indices = kotlin_kotlin.$_$.x;
  var flatten = kotlin_kotlin.$_$.v;
  var ArrayList_init_$Create$_0 = kotlin_kotlin.$_$.f;
  var listOf_0 = kotlin_kotlin.$_$.a1;
  var toMutableList = kotlin_kotlin.$_$.k1;
  var getNumberHashCode = kotlin_kotlin.$_$.v1;
  var equals = kotlin_kotlin.$_$.t1;
  var toList = kotlin_kotlin.$_$.j1;
  var firstOrNull = kotlin_kotlin.$_$.t;
  var mutableListOf = kotlin_kotlin.$_$.d1;
  var toList_0 = kotlin_kotlin.$_$.i1;
  var dropLast_0 = kotlin_kotlin.$_$.r;
  var LinkedHashSet_init_$Create$ = kotlin_kotlin.$_$.g;
  var checkIndexOverflow = kotlin_kotlin.$_$.o;
  var to = kotlin_kotlin.$_$.v2;
  var mapOf = kotlin_kotlin.$_$.c1;
  var StringBuilder_init_$Create$_0 = kotlin_kotlin.$_$.i;
  var abs = kotlin_kotlin.$_$.g2;
  var sortWith = kotlin_kotlin.$_$.g1;
  var roundToInt = kotlin_kotlin.$_$.h2;
  var emptyList = kotlin_kotlin.$_$.s;
  var joinToString = kotlin_kotlin.$_$.y;
  var FunctionAdapter = kotlin_kotlin.$_$.m1;
  var isInterface = kotlin_kotlin.$_$.a2;
  var Comparator = kotlin_kotlin.$_$.q2;
  var hashCode = kotlin_kotlin.$_$.x1;
  var Triple = kotlin_kotlin.$_$.t2;
  var compareValues = kotlin_kotlin.$_$.l1;
  var getBooleanHashCode = kotlin_kotlin.$_$.u1;
  //endregion
  //region block: pre-declaration
  initMetadataForObject(Hangul, 'Hangul');
  initMetadataForClass(RemovePolicy, 'RemovePolicy', VOID, Enum);
  initMetadataForClass(HangulContext, 'HangulContext', HangulContext);
  initMetadataForClass(Symbol, 'Symbol');
  initMetadataForObject(HangulProcessor, 'HangulProcessor');
  initMetadataForClass(LetterCategory, 'LetterCategory', VOID, Enum);
  initMetadataForClass(Letter, 'Letter');
  initMetadataForObject(Letters, 'Letters');
  initMetadataForClass(analyzeRelation$Candidate, 'Candidate');
  initMetadataForClass(HangulRecognizer, 'HangulRecognizer', HangulRecognizer);
  initMetadataForClass(deduplicateOverlappingSegments$SegmentProps, 'SegmentProps');
  initMetadataForObject(ReferenceData, 'ReferenceData');
  initMetadataForClass(ComparisonResult, 'ComparisonResult');
  initMetadataForClass(sam$kotlin_Comparator$0, 'sam$kotlin_Comparator$0', VOID, VOID, [Comparator, FunctionAdapter]);
  initMetadataForClass(sam$kotlin_Comparator$0_0, 'sam$kotlin_Comparator$0', VOID, VOID, [Comparator, FunctionAdapter]);
  initMetadataForClass(compareSignatures$StrokeMapping, 'StrokeMapping');
  initMetadataForClass(DrawingPoint, 'DrawingPoint');
  initMetadataForClass(StrokeType, 'StrokeType', VOID, Enum);
  initMetadataForClass(ReferenceStroke, 'ReferenceStroke');
  initMetadataForClass(RecognitionResult, 'RecognitionResult');
  initMetadataForClass(StrokeDirection, 'StrokeDirection', VOID, Enum);
  initMetadataForClass(AnalyzedStroke, 'AnalyzedStroke');
  initMetadataForClass(HorizontalPosition, 'HorizontalPosition', VOID, Enum);
  initMetadataForClass(VerticalPosition, 'VerticalPosition', VOID, Enum);
  initMetadataForClass(ConnectionPoint, 'ConnectionPoint', VOID, Enum);
  initMetadataForClass(StrokeRelation, 'StrokeRelation');
  initMetadataForClass(StructuralSignature, 'StructuralSignature');
  initMetadataForClass(BoundingBox, 'BoundingBox');
  //endregion
  function Hangul() {
  }
  protoOf(Hangul).g9 = function () {
    return Letters_getInstance().consonants;
  };
  protoOf(Hangul).h9 = function () {
    return Letters_getInstance().vowels;
  };
  protoOf(Hangul).i9 = function () {
    return Letters_getInstance().complexVowels;
  };
  protoOf(Hangul).j9 = function () {
    return Letters_getInstance().tenseConsonants;
  };
  protoOf(Hangul).generateRandomSyllable = function () {
    var ctx = new HangulContext();
    if (Default_getInstance().r7()) {
      // Inline function 'kotlin.collections.random' call
      var this_0 = this.consonants;
      var tmp$ret$0 = random(this_0, Default_getInstance());
      ctx.appendLetters([tmp$ret$0.character]);
    } else {
      ctx.appendLetter('\u3147');
    }
    // Inline function 'kotlin.collections.random' call
    var this_1 = this.vowels;
    var tmp$ret$1 = random(this_1, Default_getInstance());
    ctx.appendLetters([tmp$ret$1.character]);
    if (Default_getInstance().r7()) {
      // Inline function 'kotlin.collections.random' call
      var this_2 = this.complexVowels;
      var tmp$ret$2 = random(this_2, Default_getInstance());
      ctx.appendLetters([tmp$ret$2.character]);
    }
    if (Default_getInstance().s7() < 0.3) {
      // Inline function 'kotlin.collections.random' call
      var this_3 = this.tenseConsonants;
      var tmp$ret$3 = random(this_3, Default_getInstance());
      ctx.appendLetters([tmp$ret$3.character]);
    }
    var result = ctx.getValue();
    if (result.length > 1) {
      return this.generateRandomSyllable();
    }
    return result;
  };
  protoOf(Hangul).getBasicLetters = function () {
    return Letters_getInstance().getBasicLetters();
  };
  protoOf(Hangul).getAll = function () {
    return Letters_getInstance().getAll();
  };
  var Hangul_instance;
  function Hangul_getInstance() {
    return Hangul_instance;
  }
  var RemovePolicy_DEFAULT_instance;
  var RemovePolicy_REFORMAT_ON_DELETE_instance;
  function values() {
    return [RemovePolicy_DEFAULT_getInstance(), RemovePolicy_REFORMAT_ON_DELETE_getInstance()];
  }
  function valueOf(value) {
    switch (value) {
      case 'DEFAULT':
        return RemovePolicy_DEFAULT_getInstance();
      case 'REFORMAT_ON_DELETE':
        return RemovePolicy_REFORMAT_ON_DELETE_getInstance();
      default:
        RemovePolicy_initEntries();
        THROW_IAE('No enum constant RemovePolicy.' + value);
        break;
    }
  }
  var RemovePolicy_entriesInitialized;
  function RemovePolicy_initEntries() {
    if (RemovePolicy_entriesInitialized)
      return Unit_instance;
    RemovePolicy_entriesInitialized = true;
    RemovePolicy_DEFAULT_instance = new RemovePolicy('DEFAULT', 0);
    RemovePolicy_REFORMAT_ON_DELETE_instance = new RemovePolicy('REFORMAT_ON_DELETE', 1);
  }
  function RemovePolicy(name, ordinal) {
    Enum.call(this, name, ordinal);
  }
  function insertAtCaret($this, value) {
    var selectionStart = getSelectionStart($this);
    var selectionEnd = getSelectionEnd($this);
    $this.n9_1 = substring($this.n9_1, 0, selectionStart) + value + substring_0($this.n9_1, selectionEnd);
    $this.o9_1 = $this.n9_1.length;
  }
  function deleteAtCaret($this, count, to) {
    to = to === VOID ? 0 : to;
    var selectionStart = getSelectionStart($this);
    var selectionEnd = getSelectionEnd($this);
    var contentLength = $this.n9_1.length;
    // Inline function 'kotlin.math.min' call
    var deleteFrom = Math.min(count, selectionStart);
    var deleteTo = (selectionEnd + to | 0) > contentLength ? contentLength - selectionEnd | 0 : to;
    var deleted = substring($this.n9_1, selectionStart - deleteFrom | 0, selectionEnd + deleteTo | 0);
    $this.n9_1 = substring($this.n9_1, 0, selectionStart - deleteFrom | 0) + substring_0($this.n9_1, selectionEnd + deleteTo | 0);
    setCaretPosition($this, selectionStart - deleteFrom | 0);
    return deleted;
  }
  function setCaretPosition($this, position) {
    $this.o9_1 = position;
  }
  function getSelectionEnd($this) {
    return $this.o9_1;
  }
  function getSelectionStart($this) {
    return $this.o9_1;
  }
  function HangulContext(initialPhrase, removePolicy) {
    initialPhrase = initialPhrase === VOID ? '' : initialPhrase;
    removePolicy = removePolicy === VOID ? RemovePolicy_DEFAULT_getInstance() : removePolicy;
    this.m9_1 = removePolicy;
    this.n9_1 = initialPhrase;
    this.o9_1 = initialPhrase.length;
  }
  protoOf(HangulContext).clear = function () {
    this.n9_1 = '';
    this.o9_1 = 0;
  };
  protoOf(HangulContext).appendLetter = function (letter) {
    insertAtCaret(this, letter);
    var deleted = deleteAtCaret(this, 2);
    insertAtCaret(this, this.composeHangul(deleted));
  };
  protoOf(HangulContext).appendLetters = function (letters) {
    // Inline function 'kotlin.collections.forEach' call
    var inductionVariable = 0;
    var last = letters.length;
    while (inductionVariable < last) {
      var element = letters[inductionVariable];
      inductionVariable = inductionVariable + 1 | 0;
      this.appendLetter(element);
    }
  };
  protoOf(HangulContext).removeLastLetter = function () {
    var deleted = deleteAtCaret(this, 1);
    var tmp;
    // Inline function 'kotlin.text.isNotEmpty' call
    var this_0 = deleted;
    if (charSequenceLength(this_0) > 0) {
      // Inline function 'kotlin.code' call
      var this_1 = charCodeAt(deleted, 0);
      var containsArg = Char__toInt_impl_vasixd(this_1);
      tmp = 56320 <= containsArg ? containsArg <= 57343 : false;
    } else {
      tmp = false;
    }
    if (tmp) {
      deleted = deleteAtCaret(this, 1) + deleted;
    }
    var decomposed = this.decomposeHangul(deleted);
    if (decomposed.length > 1) {
      switch (this.m9_1.y1_1) {
        case 0:
          insertAtCaret(this, this.composeHangul(dropLast(decomposed, 1)));
          break;
        case 1:
          // Inline function 'kotlin.text.forEach' call

          var indexedObject = dropLast(decomposed, 1);
          var inductionVariable = 0;
          while (inductionVariable < charSequenceLength(indexedObject)) {
            var element = charSequenceGet(indexedObject, inductionVariable);
            inductionVariable = inductionVariable + 1 | 0;
            this.appendLetter(toString(element));
          }

          break;
        default:
          noWhenBranchMatchedException();
          break;
      }
    }
  };
  protoOf(HangulContext).composeHangul = function (value) {
    return HangulProcessor_getInstance().fa(value);
  };
  protoOf(HangulContext).ga = function (value) {
    var tmp = HangulProcessor_getInstance();
    return tmp.ha(value == null ? this.n9_1 : value);
  };
  protoOf(HangulContext).decomposeHangul = function (value, $super) {
    value = value === VOID ? null : value;
    return $super === VOID ? this.ga(value) : $super.ga.call(this, value);
  };
  protoOf(HangulContext).getValue = function () {
    return this.n9_1;
  };
  function RemovePolicy_DEFAULT_getInstance() {
    RemovePolicy_initEntries();
    return RemovePolicy_DEFAULT_instance;
  }
  function RemovePolicy_REFORMAT_ON_DELETE_getInstance() {
    RemovePolicy_initEntries();
    return RemovePolicy_REFORMAT_ON_DELETE_instance;
  }
  function Symbol(value, compoundOf) {
    compoundOf = compoundOf === VOID ? null : compoundOf;
    this.ia_1 = value;
    this.ja_1 = compoundOf;
  }
  function HangulProcessor() {
    HangulProcessor_instance = this;
    var tmp = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    tmp.p9_1 = [new Char(_Char___init__impl__6a9atx(12593)), new Char(_Char___init__impl__6a9atx(12594)), new Char(_Char___init__impl__6a9atx(12596)), new Char(_Char___init__impl__6a9atx(12599)), new Char(_Char___init__impl__6a9atx(12600)), new Char(_Char___init__impl__6a9atx(12601)), new Char(_Char___init__impl__6a9atx(12609)), new Char(_Char___init__impl__6a9atx(12610)), new Char(_Char___init__impl__6a9atx(12611)), new Char(_Char___init__impl__6a9atx(12613)), new Char(_Char___init__impl__6a9atx(12614)), new Char(_Char___init__impl__6a9atx(12615)), new Char(_Char___init__impl__6a9atx(12616)), new Char(_Char___init__impl__6a9atx(12617)), new Char(_Char___init__impl__6a9atx(12618)), new Char(_Char___init__impl__6a9atx(12619)), new Char(_Char___init__impl__6a9atx(12620)), new Char(_Char___init__impl__6a9atx(12621)), new Char(_Char___init__impl__6a9atx(12622))];
    var tmp_0 = this;
    // Inline function 'kotlin.collections.map' call
    var this_0 = this.p9_1;
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(this_0.length);
    var inductionVariable = 0;
    var last = this_0.length;
    while (inductionVariable < last) {
      var item = this_0[inductionVariable];
      inductionVariable = inductionVariable + 1 | 0;
      // Inline function 'kotlin.code' call
      var this_1 = item.x_1;
      var tmp$ret$5 = Char__toInt_impl_vasixd(this_1);
      destination.q(tmp$ret$5);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    tmp_0.q9_1 = copyToArray(destination);
    var tmp_1 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    tmp_1.r9_1 = [new Char(_Char___init__impl__6a9atx(0)), new Char(_Char___init__impl__6a9atx(12593)), new Char(_Char___init__impl__6a9atx(12594)), new Char(_Char___init__impl__6a9atx(12595)), new Char(_Char___init__impl__6a9atx(12596)), new Char(_Char___init__impl__6a9atx(12597)), new Char(_Char___init__impl__6a9atx(12598)), new Char(_Char___init__impl__6a9atx(12599)), new Char(_Char___init__impl__6a9atx(12601)), new Char(_Char___init__impl__6a9atx(12602)), new Char(_Char___init__impl__6a9atx(12603)), new Char(_Char___init__impl__6a9atx(12604)), new Char(_Char___init__impl__6a9atx(12605)), new Char(_Char___init__impl__6a9atx(12606)), new Char(_Char___init__impl__6a9atx(12607)), new Char(_Char___init__impl__6a9atx(12608)), new Char(_Char___init__impl__6a9atx(12609)), new Char(_Char___init__impl__6a9atx(12610)), new Char(_Char___init__impl__6a9atx(12612)), new Char(_Char___init__impl__6a9atx(12613)), new Char(_Char___init__impl__6a9atx(12614)), new Char(_Char___init__impl__6a9atx(12615)), new Char(_Char___init__impl__6a9atx(12616)), new Char(_Char___init__impl__6a9atx(12618)), new Char(_Char___init__impl__6a9atx(12619)), new Char(_Char___init__impl__6a9atx(12620)), new Char(_Char___init__impl__6a9atx(12621)), new Char(_Char___init__impl__6a9atx(12622))];
    this.s9_1 = split(',\u3131,\u3132,\u3131\u3145,\u3134,\u3134\u3148,\u3134\u314E,\u3137,\u3139,\u3139\u3131,\u3139\u3141,\u3139\u3142,\u3139\u3145,\u3139\u314C,\u3139\u314D,\u3139\u314E,\u3141,\u3142,\u3142\u3145,\u3145,\u3146,\u3147,\u3148,\u314A,\u314B,\u314C,\u314D,\u314E', [',']);
    var tmp_2 = this;
    // Inline function 'kotlin.collections.map' call
    var this_2 = this.r9_1;
    // Inline function 'kotlin.collections.mapTo' call
    var destination_0 = ArrayList_init_$Create$(this_2.length);
    var inductionVariable_0 = 0;
    var last_0 = this_2.length;
    while (inductionVariable_0 < last_0) {
      var item_0 = this_2[inductionVariable_0];
      inductionVariable_0 = inductionVariable_0 + 1 | 0;
      // Inline function 'kotlin.code' call
      var this_3 = item_0.x_1;
      var tmp$ret$13 = Char__toInt_impl_vasixd(this_3);
      destination_0.q(tmp$ret$13);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    tmp_2.t9_1 = copyToArray(destination_0);
    var tmp_3 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    tmp_3.u9_1 = [new Symbol(_Char___init__impl__6a9atx(12623)), new Symbol(_Char___init__impl__6a9atx(12624)), new Symbol(_Char___init__impl__6a9atx(12625)), new Symbol(_Char___init__impl__6a9atx(12626)), new Symbol(_Char___init__impl__6a9atx(12627)), new Symbol(_Char___init__impl__6a9atx(12628)), new Symbol(_Char___init__impl__6a9atx(12629)), new Symbol(_Char___init__impl__6a9atx(12630)), new Symbol(_Char___init__impl__6a9atx(12631)), new Symbol(_Char___init__impl__6a9atx(12632), codePointArray(charArrayOf([_Char___init__impl__6a9atx(12631), _Char___init__impl__6a9atx(12623)]))), new Symbol(_Char___init__impl__6a9atx(12633), codePointArray(charArrayOf([_Char___init__impl__6a9atx(12631), _Char___init__impl__6a9atx(12624)]))), new Symbol(_Char___init__impl__6a9atx(12634), codePointArray(charArrayOf([_Char___init__impl__6a9atx(12631), _Char___init__impl__6a9atx(12643)]))), new Symbol(_Char___init__impl__6a9atx(12635)), new Symbol(_Char___init__impl__6a9atx(12636)), new Symbol(_Char___init__impl__6a9atx(12637), codePointArray(charArrayOf([_Char___init__impl__6a9atx(12636), _Char___init__impl__6a9atx(12627)]))), new Symbol(_Char___init__impl__6a9atx(12638), codePointArray(charArrayOf([_Char___init__impl__6a9atx(12636), _Char___init__impl__6a9atx(12628)]))), new Symbol(_Char___init__impl__6a9atx(12639), codePointArray(charArrayOf([_Char___init__impl__6a9atx(12636), _Char___init__impl__6a9atx(12643)]))), new Symbol(_Char___init__impl__6a9atx(12640)), new Symbol(_Char___init__impl__6a9atx(12641)), new Symbol(_Char___init__impl__6a9atx(12642), codePointArray(charArrayOf([_Char___init__impl__6a9atx(12641), _Char___init__impl__6a9atx(12643)]))), new Symbol(_Char___init__impl__6a9atx(12643))];
    var tmp_4 = this;
    // Inline function 'kotlin.collections.map' call
    var this_4 = this.u9_1;
    // Inline function 'kotlin.collections.mapTo' call
    var destination_1 = ArrayList_init_$Create$(this_4.length);
    var inductionVariable_1 = 0;
    var last_1 = this_4.length;
    while (inductionVariable_1 < last_1) {
      var item_1 = this_4[inductionVariable_1];
      inductionVariable_1 = inductionVariable_1 + 1 | 0;
      // Inline function 'kotlin.code' call
      var this_5 = item_1.ia_1;
      var tmp$ret$21 = Char__toInt_impl_vasixd(this_5);
      destination_1.q(tmp$ret$21);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    tmp_4.v9_1 = copyToArray(destination_1);
    var tmp_5 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    tmp_5.w9_1 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 800, 801, 820, 0, 0, 1304, 1305, 1320, 0, 0, 1820];
    var tmp_6 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    tmp_6.x9_1 = [0, 0, 0, 119, 0, 422, 427, 0, 0, 801, 816, 817, 819, 825, 826, 827, 0, 0, 1719, 0, 1919];
    this.y9_1 = 44032;
    this.z9_1 = 12623;
    this.aa_1 = 21;
    this.ba_1 = 588;
    this.ca_1 = this.t9_1.length;
    this.da_1 = this.q9_1.length;
    this.ea_1 = imul(imul(this.da_1, 21), this.ca_1);
    // Inline function 'kotlin.require' call
    if (!(this.da_1 === 19)) {
      var message = 'Initial count must be equal to 19';
      throw IllegalArgumentException_init_$Create$(toString_0(message));
    }
    // Inline function 'kotlin.require' call
    if (!(this.ca_1 === 28)) {
      var message_0 = 'Finale count must be equal to 28';
      throw IllegalArgumentException_init_$Create$(toString_0(message_0));
    }
  }
  protoOf(HangulProcessor).fa = function (input) {
    // Inline function 'kotlin.text.isEmpty' call
    if (charSequenceLength(input) === 0)
      return '';
    // Inline function 'kotlin.code' call
    var this_0 = charCodeAt(input, 0);
    var syllableCode = Char__toInt_impl_vasixd(this_0);
    var result = fromCharCode(new Int32Array([syllableCode]));
    var inductionVariable = 1;
    var last = input.length;
    if (inductionVariable < last)
      $l$loop_4: do {
        var charIdx = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        // Inline function 'kotlin.code' call
        var this_1 = charCodeAt(input, charIdx);
        var currentCode = Char__toInt_impl_vasixd(this_1);
        var initialCode = indexOf(this.q9_1, syllableCode);
        var vowelDiff = currentCode - 12623 | 0;
        if (!(initialCode === -1)) {
          if (0 <= vowelDiff ? vowelDiff < 21 : false) {
            syllableCode = 44032 + imul(imul(initialCode, 21) + vowelDiff | 0, this.ca_1) | 0;
            result = sliceExclusive(result, numberRangeToNumber(0, result.length)) + fromCharCode(new Int32Array([syllableCode]));
            continue $l$loop_4;
          }
        }
        var relativeSyllableCode = syllableCode - 44032 | 0;
        var finalConsonant = relativeSyllableCode % this.ca_1 | 0;
        if ((0 <= relativeSyllableCode ? relativeSyllableCode <= 11144 : false) && finalConsonant === 0) {
          var currentFinale = indexOf(this.t9_1, currentCode);
          if (!(currentFinale === -1)) {
            syllableCode = syllableCode + currentFinale | 0;
            result = sliceExclusive(result, numberRangeToNumber(0, result.length)) + fromCharCode(new Int32Array([syllableCode]));
            continue $l$loop_4;
          }
          var mediaVowelBase = (relativeSyllableCode % 588 | 0) / this.ca_1 | 0;
          var doubledMedialVowel = indexOf(this.w9_1, imul(mediaVowelBase, 100) + (currentCode - 12623 | 0) | 0);
          if (doubledMedialVowel > 0) {
            syllableCode = syllableCode + imul(doubledMedialVowel - mediaVowelBase | 0, this.ca_1) | 0;
            result = sliceExclusive(result, numberRangeToNumber(0, result.length)) + fromCharCode(new Int32Array([syllableCode]));
            continue $l$loop_4;
          }
        }
        if ((0 <= relativeSyllableCode ? relativeSyllableCode < this.ea_1 : false) && !(finalConsonant === 0)) {
          if (0 <= vowelDiff ? vowelDiff < 21 : false) {
            initialCode = indexOf(this.q9_1, this.t9_1[finalConsonant]);
            if (0 <= initialCode ? initialCode < this.da_1 : false) {
              result = sliceExclusive(result, numberRangeToNumber(0, result.length)) + fromCharCode(new Int32Array([syllableCode - finalConsonant | 0]));
              syllableCode = 44032 + imul(imul(initialCode, 21) + vowelDiff | 0, this.ca_1) | 0;
              result = result + fromCharCode(new Int32Array([syllableCode]));
              continue $l$loop_4;
            }
            if (finalConsonant < this.x9_1.length && !(this.x9_1[finalConsonant] === 0)) {
              var tmp = sliceExclusive(result, numberRangeToNumber(0, result.length));
              var tmp_0 = syllableCode - finalConsonant | 0;
              // Inline function 'kotlin.math.floor' call
              var x = this.x9_1[finalConsonant] / 100.0;
              var tmp$ret$3 = Math.floor(x);
              result = tmp + fromCharCode(new Int32Array([tmp_0 + numberToInt(tmp$ret$3) | 0]));
              syllableCode = 44032 + imul(imul(indexOf(this.q9_1, this.t9_1[this.x9_1[finalConsonant] % 100 | 0]), 21) + vowelDiff | 0, this.ca_1) | 0;
              result = result + fromCharCode(new Int32Array([syllableCode]));
              continue $l$loop_4;
            }
          }
          var doubledFinaleConsonant = indexOf(this.x9_1, imul(finalConsonant, 100) + indexOf(this.t9_1, currentCode) | 0);
          if (doubledFinaleConsonant > 0) {
            syllableCode = (syllableCode + doubledFinaleConsonant | 0) - finalConsonant | 0;
            result = sliceExclusive(result, numberRangeToNumber(0, result.length)) + fromCharCode(new Int32Array([syllableCode]));
            continue $l$loop_4;
          }
        }
        syllableCode = currentCode;
        result = result + fromCharCode(new Int32Array([currentCode]));
      }
       while (inductionVariable < last);
    return result;
  };
  protoOf(HangulProcessor).ha = function (input) {
    var result = '';
    var inductionVariable = 0;
    var last = input.length;
    $l$loop: while (inductionVariable < last) {
      var char = charCodeAt(input, inductionVariable);
      inductionVariable = inductionVariable + 1 | 0;
      // Inline function 'kotlin.code' call
      var syllableIndex = Char__toInt_impl_vasixd(char) - 44032 | 0;
      if (syllableIndex < 0 || syllableIndex >= this.ea_1) {
        result = result + toString(char);
        continue $l$loop;
      }
      // Inline function 'kotlin.math.floor' call
      var x = syllableIndex / 588;
      var tmp$ret$1 = Math.floor(x);
      var initialCode = this.q9_1[numberToInt(tmp$ret$1)];
      var syllable = 12623 + ((syllableIndex % 588 | 0) / this.ca_1 | 0) | 0;
      var finaleCode = this.t9_1[syllableIndex % this.ca_1 | 0];
      var tmp0 = this.u9_1;
      var tmp$ret$2;
      $l$block: {
        // Inline function 'kotlin.collections.firstOrNull' call
        var inductionVariable_0 = 0;
        var last_0 = tmp0.length;
        while (inductionVariable_0 < last_0) {
          var element = tmp0[inductionVariable_0];
          inductionVariable_0 = inductionVariable_0 + 1 | 0;
          // Inline function 'kotlin.code' call
          var this_0 = element.ia_1;
          if (Char__toInt_impl_vasixd(this_0) === syllable) {
            tmp$ret$2 = element;
            break $l$block;
          }
        }
        tmp$ret$2 = null;
      }
      var tmp0_safe_receiver = tmp$ret$2;
      var tmp1_elvis_lhs = tmp0_safe_receiver == null ? null : tmp0_safe_receiver.ja_1;
      var tmp;
      if (tmp1_elvis_lhs == null) {
        // Inline function 'kotlin.intArrayOf' call
        tmp = new Int32Array([syllable]);
      } else {
        tmp = tmp1_elvis_lhs;
      }
      var syllableStructure = tmp;
      result = result + fromCharCode(primitiveArrayConcat([new Int32Array([initialCode]), syllableStructure]));
      if (!(finaleCode === 0)) {
        result = result + this.s9_1.o(syllableIndex % this.ca_1 | 0);
      }
    }
    return result;
  };
  var HangulProcessor_instance;
  function HangulProcessor_getInstance() {
    if (HangulProcessor_instance == null)
      new HangulProcessor();
    return HangulProcessor_instance;
  }
  function sliceExclusive(_this__u8e3s4, range) {
    return slice(_this__u8e3s4, until(range.b8_1, range.c8_1 - 2 | 0));
  }
  function fromCharCode(codePoints) {
    var builder = StringBuilder_init_$Create$(codePoints.length);
    var inductionVariable = 0;
    var last = codePoints.length;
    while (inductionVariable < last) {
      var codePoint = codePoints[inductionVariable];
      inductionVariable = inductionVariable + 1 | 0;
      appendCodePoint(builder, codePoint);
    }
    return builder.toString();
  }
  function codePointArray(chars) {
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(chars.length);
    var inductionVariable = 0;
    var last = chars.length;
    while (inductionVariable < last) {
      var item = chars[inductionVariable];
      inductionVariable = inductionVariable + 1 | 0;
      // Inline function 'kotlin.code' call
      var tmp$ret$2 = Char__toInt_impl_vasixd(item);
      destination.q(tmp$ret$2);
    }
    return toIntArray(destination);
  }
  var LetterCategory_CONSONANT_instance;
  var LetterCategory_TENSE_CONSONANT_instance;
  var LetterCategory_VOWEL_instance;
  var LetterCategory_COMPLEX_VOWEL_instance;
  function values_0() {
    return [LetterCategory_CONSONANT_getInstance(), LetterCategory_TENSE_CONSONANT_getInstance(), LetterCategory_VOWEL_getInstance(), LetterCategory_COMPLEX_VOWEL_getInstance()];
  }
  function valueOf_0(value) {
    switch (value) {
      case 'CONSONANT':
        return LetterCategory_CONSONANT_getInstance();
      case 'TENSE_CONSONANT':
        return LetterCategory_TENSE_CONSONANT_getInstance();
      case 'VOWEL':
        return LetterCategory_VOWEL_getInstance();
      case 'COMPLEX_VOWEL':
        return LetterCategory_COMPLEX_VOWEL_getInstance();
      default:
        LetterCategory_initEntries();
        THROW_IAE('No enum constant LetterCategory.' + value);
        break;
    }
  }
  var LetterCategory_entriesInitialized;
  function LetterCategory_initEntries() {
    if (LetterCategory_entriesInitialized)
      return Unit_instance;
    LetterCategory_entriesInitialized = true;
    LetterCategory_CONSONANT_instance = new LetterCategory('CONSONANT', 0);
    LetterCategory_TENSE_CONSONANT_instance = new LetterCategory('TENSE_CONSONANT', 1);
    LetterCategory_VOWEL_instance = new LetterCategory('VOWEL', 2);
    LetterCategory_COMPLEX_VOWEL_instance = new LetterCategory('COMPLEX_VOWEL', 3);
  }
  function LetterCategory(name, ordinal) {
    Enum.call(this, name, ordinal);
  }
  function Letter(character, name, romanization, category) {
    this.character = character;
    this.name = name;
    this.romanization = romanization;
    this.category = category;
  }
  protoOf(Letter).ma = function () {
    return this.character;
  };
  protoOf(Letter).z1 = function () {
    return this.name;
  };
  protoOf(Letter).na = function () {
    return this.romanization;
  };
  protoOf(Letter).oa = function () {
    return this.category;
  };
  protoOf(Letter).toString = function () {
    return this.character;
  };
  protoOf(Letter).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof Letter))
      return false;
    return this.character === other.character;
  };
  protoOf(Letter).hashCode = function () {
    return getStringHashCode(this.character);
  };
  function LetterCategory_CONSONANT_getInstance() {
    LetterCategory_initEntries();
    return LetterCategory_CONSONANT_instance;
  }
  function LetterCategory_TENSE_CONSONANT_getInstance() {
    LetterCategory_initEntries();
    return LetterCategory_TENSE_CONSONANT_instance;
  }
  function LetterCategory_VOWEL_getInstance() {
    LetterCategory_initEntries();
    return LetterCategory_VOWEL_instance;
  }
  function LetterCategory_COMPLEX_VOWEL_getInstance() {
    LetterCategory_initEntries();
    return LetterCategory_COMPLEX_VOWEL_instance;
  }
  function Letters() {
    Letters_instance = this;
    var tmp = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$0 = ['g', 'k'];
    tmp.ㄱ = new Letter('\u3131', 'giyeok', tmp$ret$0, LetterCategory_CONSONANT_getInstance());
    var tmp_0 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$3 = ['n'];
    tmp_0.ㄴ = new Letter('\u3134', 'nieun', tmp$ret$3, LetterCategory_CONSONANT_getInstance());
    var tmp_1 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$6 = ['d', 't'];
    tmp_1.ㄷ = new Letter('\u3137', 'digeut', tmp$ret$6, LetterCategory_CONSONANT_getInstance());
    var tmp_2 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$9 = ['r', 'l'];
    tmp_2.ㄹ = new Letter('\u3139', 'rieul', tmp$ret$9, LetterCategory_CONSONANT_getInstance());
    var tmp_3 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$12 = ['m'];
    tmp_3.ㅁ = new Letter('\u3141', 'mieum', tmp$ret$12, LetterCategory_CONSONANT_getInstance());
    var tmp_4 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$15 = ['b', 'p'];
    tmp_4.ㅂ = new Letter('\u3142', 'bieup', tmp$ret$15, LetterCategory_CONSONANT_getInstance());
    var tmp_5 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$18 = ['s'];
    tmp_5.ㅅ = new Letter('\u3145', 'siot', tmp$ret$18, LetterCategory_CONSONANT_getInstance());
    var tmp_6 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$21 = ['ng'];
    tmp_6.ㅇ = new Letter('\u3147', 'ieung', tmp$ret$21, LetterCategory_CONSONANT_getInstance());
    var tmp_7 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$24 = ['j'];
    tmp_7.ㅈ = new Letter('\u3148', 'jieut', tmp$ret$24, LetterCategory_CONSONANT_getInstance());
    var tmp_8 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$27 = ['ch'];
    tmp_8.ㅊ = new Letter('\u314A', 'chieut', tmp$ret$27, LetterCategory_CONSONANT_getInstance());
    var tmp_9 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$30 = ['k'];
    tmp_9.ㅋ = new Letter('\u314B', 'kieuk', tmp$ret$30, LetterCategory_CONSONANT_getInstance());
    var tmp_10 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$33 = ['t'];
    tmp_10.ㅌ = new Letter('\u314C', 'tieut', tmp$ret$33, LetterCategory_CONSONANT_getInstance());
    var tmp_11 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$36 = ['p'];
    tmp_11.ㅍ = new Letter('\u314D', 'pieup', tmp$ret$36, LetterCategory_CONSONANT_getInstance());
    var tmp_12 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$39 = ['h'];
    tmp_12.ㅎ = new Letter('\u314E', 'hieut', tmp$ret$39, LetterCategory_CONSONANT_getInstance());
    this.consonants = listOf([this.ㄱ, this.ㄴ, this.ㄷ, this.ㄹ, this.ㅁ, this.ㅂ, this.ㅅ, this.ㅇ, this.ㅈ, this.ㅊ, this.ㅋ, this.ㅌ, this.ㅍ, this.ㅎ]);
    var tmp_13 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$42 = ['kk'];
    tmp_13.ㄲ = new Letter('\u3132', 'ssang-giyeok', tmp$ret$42, LetterCategory_TENSE_CONSONANT_getInstance());
    var tmp_14 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$45 = ['tt'];
    tmp_14.ㄸ = new Letter('\u3138', 'ssang-digeut', tmp$ret$45, LetterCategory_TENSE_CONSONANT_getInstance());
    var tmp_15 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$48 = ['pp'];
    tmp_15.ㅃ = new Letter('\u3143', 'ssang-bieup', tmp$ret$48, LetterCategory_TENSE_CONSONANT_getInstance());
    var tmp_16 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$51 = ['ss'];
    tmp_16.ㅆ = new Letter('\u3146', 'ssang-siot', tmp$ret$51, LetterCategory_TENSE_CONSONANT_getInstance());
    var tmp_17 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$54 = ['jj'];
    tmp_17.ㅉ = new Letter('\u3149', 'ssang-jieut', tmp$ret$54, LetterCategory_TENSE_CONSONANT_getInstance());
    this.tenseConsonants = listOf([this.ㄲ, this.ㄸ, this.ㅃ, this.ㅆ, this.ㅉ]);
    var tmp_18 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$57 = ['a'];
    tmp_18.ㅏ = new Letter('\u314F', 'a', tmp$ret$57, LetterCategory_VOWEL_getInstance());
    var tmp_19 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$60 = ['ya'];
    tmp_19.ㅑ = new Letter('\u3151', 'ya', tmp$ret$60, LetterCategory_VOWEL_getInstance());
    var tmp_20 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$63 = ['eo'];
    tmp_20.ㅓ = new Letter('\u3153', 'eo', tmp$ret$63, LetterCategory_VOWEL_getInstance());
    var tmp_21 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$66 = ['yeo'];
    tmp_21.ㅕ = new Letter('\u3155', 'yeo', tmp$ret$66, LetterCategory_VOWEL_getInstance());
    var tmp_22 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$69 = ['o'];
    tmp_22.ㅗ = new Letter('\u3157', 'o', tmp$ret$69, LetterCategory_VOWEL_getInstance());
    var tmp_23 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$72 = ['yo'];
    tmp_23.ㅛ = new Letter('\u315B', 'yo', tmp$ret$72, LetterCategory_VOWEL_getInstance());
    var tmp_24 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$75 = ['u'];
    tmp_24.ㅜ = new Letter('\u315C', 'u', tmp$ret$75, LetterCategory_VOWEL_getInstance());
    var tmp_25 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$78 = ['yu'];
    tmp_25.ㅠ = new Letter('\u3160', 'yu', tmp$ret$78, LetterCategory_VOWEL_getInstance());
    var tmp_26 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$81 = ['eu'];
    tmp_26.ㅡ = new Letter('\u3161', 'eu', tmp$ret$81, LetterCategory_VOWEL_getInstance());
    var tmp_27 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$84 = ['i'];
    tmp_27.ㅣ = new Letter('\u3163', 'i', tmp$ret$84, LetterCategory_VOWEL_getInstance());
    this.vowels = listOf([this.ㅏ, this.ㅑ, this.ㅓ, this.ㅕ, this.ㅗ, this.ㅛ, this.ㅜ, this.ㅠ, this.ㅡ, this.ㅣ]);
    var tmp_28 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$87 = ['ae'];
    tmp_28.ㅐ = new Letter('\u3150', 'ae', tmp$ret$87, LetterCategory_COMPLEX_VOWEL_getInstance());
    var tmp_29 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$90 = ['e'];
    tmp_29.ㅔ = new Letter('\u3154', 'e', tmp$ret$90, LetterCategory_COMPLEX_VOWEL_getInstance());
    var tmp_30 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$93 = ['yae'];
    tmp_30.ㅒ = new Letter('\u3152', 'yae', tmp$ret$93, LetterCategory_COMPLEX_VOWEL_getInstance());
    var tmp_31 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$96 = ['ye'];
    tmp_31.ㅖ = new Letter('\u3156', 'ye', tmp$ret$96, LetterCategory_COMPLEX_VOWEL_getInstance());
    var tmp_32 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$99 = ['wa'];
    tmp_32.ㅘ = new Letter('\u3158', 'wa', tmp$ret$99, LetterCategory_COMPLEX_VOWEL_getInstance());
    var tmp_33 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$102 = ['wo'];
    tmp_33.ㅝ = new Letter('\u315D', 'wo', tmp$ret$102, LetterCategory_COMPLEX_VOWEL_getInstance());
    var tmp_34 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$105 = ['wae'];
    tmp_34.ㅙ = new Letter('\u3159', 'wae', tmp$ret$105, LetterCategory_COMPLEX_VOWEL_getInstance());
    var tmp_35 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$108 = ['we'];
    tmp_35.ㅞ = new Letter('\u315E', 'we', tmp$ret$108, LetterCategory_COMPLEX_VOWEL_getInstance());
    var tmp_36 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$111 = ['oe'];
    tmp_36.ㅚ = new Letter('\u315A', 'oe', tmp$ret$111, LetterCategory_COMPLEX_VOWEL_getInstance());
    var tmp_37 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$114 = ['wi'];
    tmp_37.ㅟ = new Letter('\u315F', 'wi', tmp$ret$114, LetterCategory_COMPLEX_VOWEL_getInstance());
    var tmp_38 = this;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp$ret$117 = ['ui'];
    tmp_38.ㅢ = new Letter('\u3162', 'ui', tmp$ret$117, LetterCategory_COMPLEX_VOWEL_getInstance());
    this.complexVowels = listOf([this.ㅐ, this.ㅔ, this.ㅒ, this.ㅖ, this.ㅘ, this.ㅝ, this.ㅙ, this.ㅞ, this.ㅚ, this.ㅟ, this.ㅢ]);
  }
  protoOf(Letters).pa = function () {
    return this.ㄱ;
  };
  protoOf(Letters).qa = function () {
    return this.ㄴ;
  };
  protoOf(Letters).ra = function () {
    return this.ㄷ;
  };
  protoOf(Letters).sa = function () {
    return this.ㄹ;
  };
  protoOf(Letters).ta = function () {
    return this.ㅁ;
  };
  protoOf(Letters).ua = function () {
    return this.ㅂ;
  };
  protoOf(Letters).va = function () {
    return this.ㅅ;
  };
  protoOf(Letters).wa = function () {
    return this.ㅇ;
  };
  protoOf(Letters).xa = function () {
    return this.ㅈ;
  };
  protoOf(Letters).ya = function () {
    return this.ㅊ;
  };
  protoOf(Letters).za = function () {
    return this.ㅋ;
  };
  protoOf(Letters).ab = function () {
    return this.ㅌ;
  };
  protoOf(Letters).bb = function () {
    return this.ㅍ;
  };
  protoOf(Letters).cb = function () {
    return this.ㅎ;
  };
  protoOf(Letters).g9 = function () {
    return this.consonants;
  };
  protoOf(Letters).db = function () {
    return this.ㄲ;
  };
  protoOf(Letters).eb = function () {
    return this.ㄸ;
  };
  protoOf(Letters).fb = function () {
    return this.ㅃ;
  };
  protoOf(Letters).gb = function () {
    return this.ㅆ;
  };
  protoOf(Letters).hb = function () {
    return this.ㅉ;
  };
  protoOf(Letters).j9 = function () {
    return this.tenseConsonants;
  };
  protoOf(Letters).ib = function () {
    return this.ㅏ;
  };
  protoOf(Letters).jb = function () {
    return this.ㅑ;
  };
  protoOf(Letters).kb = function () {
    return this.ㅓ;
  };
  protoOf(Letters).lb = function () {
    return this.ㅕ;
  };
  protoOf(Letters).mb = function () {
    return this.ㅗ;
  };
  protoOf(Letters).nb = function () {
    return this.ㅛ;
  };
  protoOf(Letters).ob = function () {
    return this.ㅜ;
  };
  protoOf(Letters).pb = function () {
    return this.ㅠ;
  };
  protoOf(Letters).qb = function () {
    return this.ㅡ;
  };
  protoOf(Letters).rb = function () {
    return this.ㅣ;
  };
  protoOf(Letters).h9 = function () {
    return this.vowels;
  };
  protoOf(Letters).sb = function () {
    return this.ㅐ;
  };
  protoOf(Letters).tb = function () {
    return this.ㅔ;
  };
  protoOf(Letters).ub = function () {
    return this.ㅒ;
  };
  protoOf(Letters).vb = function () {
    return this.ㅖ;
  };
  protoOf(Letters).wb = function () {
    return this.ㅘ;
  };
  protoOf(Letters).xb = function () {
    return this.ㅝ;
  };
  protoOf(Letters).yb = function () {
    return this.ㅙ;
  };
  protoOf(Letters).zb = function () {
    return this.ㅞ;
  };
  protoOf(Letters).ac = function () {
    return this.ㅚ;
  };
  protoOf(Letters).bc = function () {
    return this.ㅟ;
  };
  protoOf(Letters).cc = function () {
    return this.ㅢ;
  };
  protoOf(Letters).i9 = function () {
    return this.complexVowels;
  };
  protoOf(Letters).getBasicLetters = function () {
    return plus(this.consonants, this.vowels);
  };
  protoOf(Letters).getAll = function () {
    return plus(plus(plus(this.consonants, this.tenseConsonants), this.vowels), this.complexVowels);
  };
  protoOf(Letters).findByCharacter = function (character) {
    // Inline function 'kotlin.collections.find' call
    var tmp0 = this.getAll();
    var tmp$ret$1;
    $l$block: {
      // Inline function 'kotlin.collections.firstOrNull' call
      var _iterator__ex2g4s = tmp0.f();
      while (_iterator__ex2g4s.g()) {
        var element = _iterator__ex2g4s.h();
        if (element.character === character) {
          tmp$ret$1 = element;
          break $l$block;
        }
      }
      tmp$ret$1 = null;
    }
    return tmp$ret$1;
  };
  var Letters_instance;
  function Letters_getInstance() {
    if (Letters_instance == null)
      new Letters();
    return Letters_instance;
  }
  function get_SELF_RELATION() {
    _init_properties_Analysis_kt__yoxa10();
    return SELF_RELATION;
  }
  var SELF_RELATION;
  function analyzeStroke(points, bbox) {
    _init_properties_Analysis_kt__yoxa10();
    if (points.i() < 2)
      return null;
    var tmp0 = bbox.hc();
    // Inline function 'kotlin.comparisons.maxOf' call
    var b = bbox.ic();
    // Inline function 'kotlin.let' call
    var it = Math.max(tmp0, b);
    var scale = it === 0.0 ? 1.0 : it;
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(points, 10));
    var _iterator__ex2g4s = points.f();
    while (_iterator__ex2g4s.g()) {
      var item = _iterator__ex2g4s.h();
      var tmp$ret$5 = analyzeStroke$normalize(bbox, scale, item);
      destination.q(tmp$ret$5);
    }
    var normalizedPoints = destination;
    var start = first(normalizedPoints);
    var end = last(normalizedPoints);
    var sumX = 0.0;
    var sumY = 0.0;
    var minX = 1.7976931348623157E308;
    var maxX = -1.7976931348623157E308;
    var minY = 1.7976931348623157E308;
    var maxY = -1.7976931348623157E308;
    var _iterator__ex2g4s_0 = normalizedPoints.f();
    while (_iterator__ex2g4s_0.g()) {
      var p = _iterator__ex2g4s_0.h();
      sumX = sumX + p.x;
      sumY = sumY + p.y;
      if (p.x < minX)
        minX = p.x;
      if (p.x > maxX)
        maxX = p.x;
      if (p.y < minY)
        minY = p.y;
      if (p.y > maxY)
        maxY = p.y;
    }
    var center = new DrawingPoint(sumX / normalizedPoints.i(), sumY / normalizedPoints.i());
    var dx = end.x - start.x;
    var dy = end.y - start.y;
    // Inline function 'kotlin.math.atan2' call
    var angle = Math.atan2(dy, dx) * (180.0 / 3.141592653589793);
    // Inline function 'kotlin.math.sqrt' call
    var x = dx * dx + dy * dy;
    var length = Math.sqrt(x);
    var maxDeviation = 0.0;
    var lenSq = length * length;
    var _iterator__ex2g4s_1 = normalizedPoints.f();
    while (_iterator__ex2g4s_1.g()) {
      var np = _iterator__ex2g4s_1.h();
      var t = lenSq > 0.0 ? coerceIn(((np.x - start.x) * dx + (np.y - start.y) * dy) / lenSq, 0.0, 1.0) : 0.0;
      var projX = start.x + t * dx;
      var projY = start.y + t * dy;
      // Inline function 'kotlin.math.pow' call
      var this_0 = np.x - projX;
      var tmp = Math.pow(this_0, 2);
      // Inline function 'kotlin.math.pow' call
      var this_1 = np.y - projY;
      // Inline function 'kotlin.math.sqrt' call
      var x_0 = tmp + Math.pow(this_1, 2);
      var deviation = Math.sqrt(x_0);
      // Inline function 'kotlin.comparisons.maxOf' call
      var a = maxDeviation;
      maxDeviation = Math.max(a, deviation);
    }
    var isCurved = maxDeviation > 0.35;
    var direction = classifyDirection(angle, length);
    return new AnalyzedStroke(direction, start, end, center, minX, maxX, minY, maxY, angle, length, isCurved);
  }
  function analyzeRelation(stroke1, stroke2) {
    _init_properties_Analysis_kt__yoxa10();
    var stroke2Mid = new DrawingPoint((stroke2.kc_1.x + stroke2.lc_1.x) / 2.0, (stroke2.kc_1.y + stroke2.lc_1.y) / 2.0);
    var stroke2Quarter = new DrawingPoint(stroke2.kc_1.x * 0.75 + stroke2.lc_1.x * 0.25, stroke2.kc_1.y * 0.75 + stroke2.lc_1.y * 0.25);
    var stroke2ThreeQuarter = new DrawingPoint(stroke2.kc_1.x * 0.25 + stroke2.lc_1.x * 0.75, stroke2.kc_1.y * 0.25 + stroke2.lc_1.y * 0.75);
    var candidates = listOf([new analyzeRelation$Candidate('start-start', distance(stroke1.kc_1, stroke2.kc_1), ConnectionPoint_START_getInstance()), new analyzeRelation$Candidate('start-quarter', distance(stroke1.kc_1, stroke2Quarter), ConnectionPoint_START_getInstance()), new analyzeRelation$Candidate('start-mid', distance(stroke1.kc_1, stroke2Mid), ConnectionPoint_MIDDLE_getInstance()), new analyzeRelation$Candidate('start-3quarter', distance(stroke1.kc_1, stroke2ThreeQuarter), ConnectionPoint_END_getInstance()), new analyzeRelation$Candidate('start-end', distance(stroke1.kc_1, stroke2.lc_1), ConnectionPoint_END_getInstance()), new analyzeRelation$Candidate('end-start', distance(stroke1.lc_1, stroke2.kc_1), ConnectionPoint_START_getInstance()), new analyzeRelation$Candidate('end-quarter', distance(stroke1.lc_1, stroke2Quarter), ConnectionPoint_START_getInstance()), new analyzeRelation$Candidate('end-mid', distance(stroke1.lc_1, stroke2Mid), ConnectionPoint_MIDDLE_getInstance()), new analyzeRelation$Candidate('end-3quarter', distance(stroke1.lc_1, stroke2ThreeQuarter), ConnectionPoint_END_getInstance()), new analyzeRelation$Candidate('end-end', distance(stroke1.lc_1, stroke2.lc_1), ConnectionPoint_END_getInstance())]);
    var tmp$ret$0;
    $l$block: {
      // Inline function 'kotlin.collections.minBy' call
      var iterator = candidates.f();
      if (!iterator.g())
        throw NoSuchElementException_init_$Create$();
      var minElem = iterator.h();
      if (!iterator.g()) {
        tmp$ret$0 = minElem;
        break $l$block;
      }
      var minValue = minElem.wc_1;
      do {
        var e = iterator.h();
        var v = e.wc_1;
        if (compareTo(minValue, v) > 0) {
          minElem = e;
          minValue = v;
        }
      }
       while (iterator.g());
      tmp$ret$0 = minElem;
    }
    var closest = tmp$ret$0;
    var connected = closest.wc_1 < 0.15;
    var tmp;
    if (connected) {
      // Inline function 'kotlin.comparisons.maxOf' call
      var b = 1.0 - closest.wc_1 / 0.15;
      tmp = Math.max(0.0, b);
    } else {
      tmp = 0.0;
    }
    var connectionQuality = tmp;
    var stroke1CenterX = stroke1.mc_1.x;
    var stroke2CenterX = stroke2.mc_1.x;
    var stroke2Width = stroke2.oc_1 - stroke2.nc_1;
    // Inline function 'kotlin.comparisons.maxOf' call
    var b_0 = stroke2Width * 0.3;
    var hThreshold = Math.max(0.05, b_0);
    var horizontalPosition = stroke1CenterX < stroke2CenterX - hThreshold ? HorizontalPosition_LEFT_OF_getInstance() : stroke1CenterX > stroke2CenterX + hThreshold ? HorizontalPosition_RIGHT_OF_getInstance() : HorizontalPosition_OVERLAPPING_getInstance();
    var stroke1CenterY = stroke1.mc_1.y;
    var stroke2CenterY = stroke2.mc_1.y;
    var stroke2Height = stroke2.qc_1 - stroke2.pc_1;
    // Inline function 'kotlin.comparisons.maxOf' call
    var b_1 = stroke2Height * 0.3;
    var vThreshold = Math.max(0.05, b_1);
    var verticalPosition = stroke1CenterY < stroke2CenterY - vThreshold ? VerticalPosition_ABOVE_getInstance() : stroke1CenterY > stroke2CenterY + vThreshold ? VerticalPosition_BELOW_getInstance() : VerticalPosition_OVERLAPPING_getInstance();
    return new StrokeRelation(connected, connected ? closest.vc_1 : 'none', connected ? closest.xc_1 : ConnectionPoint_NONE_getInstance(), connectionQuality, horizontalPosition, verticalPosition);
  }
  function buildRelationMatrix(strokes) {
    _init_properties_Analysis_kt__yoxa10();
    // Inline function 'kotlin.collections.map' call
    var this_0 = get_indices(strokes);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(this_0, 10));
    var inductionVariable = this_0.b8_1;
    var last = this_0.c8_1;
    if (inductionVariable <= last)
      do {
        var item = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var i = item;
        // Inline function 'kotlin.collections.map' call
        var this_1 = get_indices(strokes);
        // Inline function 'kotlin.collections.mapTo' call
        var destination_0 = ArrayList_init_$Create$(collectionSizeOrDefault(this_1, 10));
        var inductionVariable_0 = this_1.b8_1;
        var last_0 = this_1.c8_1;
        if (inductionVariable_0 <= last_0)
          do {
            var item_0 = inductionVariable_0;
            inductionVariable_0 = inductionVariable_0 + 1 | 0;
            var j = item_0;
            var tmp$ret$5 = i === j ? get_SELF_RELATION() : analyzeRelation(strokes.o(i), strokes.o(j));
            destination_0.q(tmp$ret$5);
          }
           while (!(item_0 === last_0));
        destination.q(destination_0);
      }
       while (!(item === last));
    return destination;
  }
  function createSignature(paths, flexible) {
    flexible = flexible === VOID ? false : flexible;
    _init_properties_Analysis_kt__yoxa10();
    if (paths.n())
      return null;
    var allPoints = flatten(paths);
    var tmp0_elvis_lhs = getBoundingBox(allPoints);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var bbox = tmp;
    // Inline function 'kotlin.collections.mutableListOf' call
    var segments = ArrayList_init_$Create$_0();
    // Inline function 'kotlin.collections.mutableListOf' call
    var circleStrokes = ArrayList_init_$Create$_0();
    var _iterator__ex2g4s = paths.f();
    while (_iterator__ex2g4s.g()) {
      var path = _iterator__ex2g4s.h();
      var pathBbox = getBoundingBox(path);
      if (!(pathBbox == null) && detectCircularPattern(listOf_0(path), pathBbox)) {
        var tmp0 = bbox.hc();
        // Inline function 'kotlin.comparisons.maxOf' call
        var b = bbox.ic();
        // Inline function 'kotlin.let' call
        var it = Math.max(tmp0, b);
        var scale = it === 0.0 ? 1.0 : it;
        var tmp_0 = StrokeDirection_CIRCLE_getInstance();
        var tmp_1 = new DrawingPoint((pathBbox.dc_1 + pathBbox.hc() / 2.0 - bbox.dc_1) / scale, (pathBbox.ec_1 - bbox.ec_1) / scale);
        var tmp_2 = new DrawingPoint((pathBbox.dc_1 + pathBbox.hc() / 2.0 - bbox.dc_1) / scale, (pathBbox.ec_1 - bbox.ec_1) / scale);
        var tmp_3 = new DrawingPoint((pathBbox.dc_1 + pathBbox.hc() / 2.0 - bbox.dc_1) / scale, (pathBbox.ec_1 + pathBbox.ic() / 2.0 - bbox.ec_1) / scale);
        var tmp_4 = (pathBbox.dc_1 - bbox.dc_1) / scale;
        var tmp_5 = (pathBbox.fc_1 - bbox.dc_1) / scale;
        var tmp_6 = (pathBbox.ec_1 - bbox.ec_1) / scale;
        var tmp_7 = (pathBbox.gc_1 - bbox.ec_1) / scale;
        var tmp0_0 = pathBbox.hc();
        // Inline function 'kotlin.comparisons.maxOf' call
        var b_0 = pathBbox.ic();
        var tmp$ret$5 = Math.max(tmp0_0, b_0);
        circleStrokes.q(new AnalyzedStroke(tmp_0, tmp_1, tmp_2, tmp_3, tmp_4, tmp_5, tmp_6, tmp_7, 0.0, 3.141592653589793 * tmp$ret$5 / scale, true));
      } else if (flexible) {
        segments.m(splitPathAtDirectionFlips(path));
      } else {
        segments.m(splitPathAtCorners(path, 50.0));
      }
    }
    if (!flexible) {
      segments = toMutableList(mergeConnectedSegments(segments, bbox));
    }
    segments = toMutableList(deduplicateOverlappingSegments(segments, bbox));
    var tmp_8;
    // Inline function 'kotlin.collections.isNotEmpty' call
    if (!circleStrokes.n()) {
      tmp_8 = segments.n();
    } else {
      tmp_8 = false;
    }
    if (tmp_8) {
      var relations = buildRelationMatrix(circleStrokes);
      return new StructuralSignature(circleStrokes.i(), circleStrokes, relations);
    }
    var strokes = toMutableList(circleStrokes);
    var _iterator__ex2g4s_0 = segments.f();
    while (_iterator__ex2g4s_0.g()) {
      var segment = _iterator__ex2g4s_0.h();
      var analyzed = analyzeStroke(segment, bbox);
      if (!(analyzed == null) && analyzed.sc_1 > 0.1) {
        strokes.q(analyzed);
      }
    }
    if (strokes.n())
      return null;
    var relations_0 = buildRelationMatrix(strokes);
    return new StructuralSignature(strokes.i(), strokes, relations_0);
  }
  function createRefSignature(strokeDefs) {
    _init_properties_Analysis_kt__yoxa10();
    // Inline function 'kotlin.collections.mutableListOf' call
    var allPoints = ArrayList_init_$Create$_0();
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(strokeDefs, 10));
    var _iterator__ex2g4s = strokeDefs.f();
    while (_iterator__ex2g4s.g()) {
      var item = _iterator__ex2g4s.h();
      var points = sampleStrokePath(item, 20);
      allPoints.m(points);
      destination.q(points);
    }
    var paths = destination;
    var tmp0_elvis_lhs = getBoundingBox(allPoints);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return null;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var bbox = tmp;
    // Inline function 'kotlin.collections.mutableListOf' call
    var strokes = ArrayList_init_$Create$_0();
    var inductionVariable = 0;
    var last = paths.i() - 1 | 0;
    if (inductionVariable <= last)
      $l$loop: do {
        var i = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var tmp1_elvis_lhs = analyzeStroke(paths.o(i), bbox);
        var tmp_0;
        if (tmp1_elvis_lhs == null) {
          continue $l$loop;
        } else {
          tmp_0 = tmp1_elvis_lhs;
        }
        var analyzed = tmp_0;
        var modified;
        switch (strokeDefs.o(i).type.y1_1) {
          case 1:
            modified = analyzed.yc(VOID, VOID, VOID, VOID, VOID, VOID, VOID, VOID, VOID, VOID, true, true);
            break;
          case 2:
            modified = analyzed.yc(StrokeDirection_CIRCLE_getInstance(), VOID, VOID, VOID, VOID, VOID, VOID, VOID, VOID, VOID, true);
            break;
          default:
            modified = analyzed;
            break;
        }
        strokes.q(modified);
      }
       while (inductionVariable <= last);
    if (strokes.n())
      return null;
    var relations = buildRelationMatrix(strokes);
    return new StructuralSignature(strokes.i(), strokes, relations);
  }
  function analyzeStroke$normalize($bbox, scale, p) {
    return new DrawingPoint((p.x - $bbox.dc_1) / scale, (p.y - $bbox.ec_1) / scale);
  }
  function analyzeRelation$Candidate(type, dist, point) {
    this.vc_1 = type;
    this.wc_1 = dist;
    this.xc_1 = point;
  }
  protoOf(analyzeRelation$Candidate).toString = function () {
    return 'Candidate(type=' + this.vc_1 + ', dist=' + this.wc_1 + ', point=' + this.xc_1.toString() + ')';
  };
  protoOf(analyzeRelation$Candidate).hashCode = function () {
    var result = getStringHashCode(this.vc_1);
    result = imul(result, 31) + getNumberHashCode(this.wc_1) | 0;
    result = imul(result, 31) + this.xc_1.hashCode() | 0;
    return result;
  };
  protoOf(analyzeRelation$Candidate).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof analyzeRelation$Candidate))
      return false;
    if (!(this.vc_1 === other.vc_1))
      return false;
    if (!equals(this.wc_1, other.wc_1))
      return false;
    if (!this.xc_1.equals(other.xc_1))
      return false;
    return true;
  };
  var properties_initialized_Analysis_kt_ihbhia;
  function _init_properties_Analysis_kt__yoxa10() {
    if (!properties_initialized_Analysis_kt_ihbhia) {
      properties_initialized_Analysis_kt_ihbhia = true;
      SELF_RELATION = new StrokeRelation(false, 'self', ConnectionPoint_NONE_getInstance(), 0.0, HorizontalPosition_OVERLAPPING_getInstance(), VerticalPosition_OVERLAPPING_getInstance());
    }
  }
  function distance(a, b) {
    // Inline function 'kotlin.math.pow' call
    var this_0 = a.x - b.x;
    var tmp = Math.pow(this_0, 2);
    // Inline function 'kotlin.math.pow' call
    var this_1 = a.y - b.y;
    // Inline function 'kotlin.math.sqrt' call
    var x = tmp + Math.pow(this_1, 2);
    return Math.sqrt(x);
  }
  function getBoundingBox(points) {
    if (points.n())
      return null;
    var minX = 1.7976931348623157E308;
    var minY = 1.7976931348623157E308;
    var maxX = -1.7976931348623157E308;
    var maxY = -1.7976931348623157E308;
    var _iterator__ex2g4s = points.f();
    while (_iterator__ex2g4s.g()) {
      var p = _iterator__ex2g4s.h();
      if (p.x < minX)
        minX = p.x;
      if (p.y < minY)
        minY = p.y;
      if (p.x > maxX)
        maxX = p.x;
      if (p.y > maxY)
        maxY = p.y;
    }
    return new BoundingBox(minX, minY, maxX, maxY);
  }
  function classifyDirection(angle, length) {
    if (length < 0.05)
      return StrokeDirection_HORIZONTAL_getInstance();
    var normalized = (angle % 360.0 + 360.0) % 360.0;
    var absAngle = normalized % 180.0;
    if (absAngle > 90.0)
      absAngle = 180.0 - absAngle;
    return absAngle <= 45.0 ? StrokeDirection_HORIZONTAL_getInstance() : StrokeDirection_VERTICAL_getInstance();
  }
  function pointToLineDistance(px, py, x1, y1, x2, y2) {
    var dx = x2 - x1;
    var dy = y2 - y1;
    var lenSq = dx * dx + dy * dy;
    if (lenSq < 0.001) {
      // Inline function 'kotlin.math.pow' call
      var this_0 = px - x1;
      var tmp = Math.pow(this_0, 2);
      // Inline function 'kotlin.math.pow' call
      var this_1 = py - y1;
      // Inline function 'kotlin.math.sqrt' call
      var x = tmp + Math.pow(this_1, 2);
      return Math.sqrt(x);
    }
    var t = ((px - x1) * dx + (py - y1) * dy) / lenSq;
    var clamped = coerceIn(t, 0.0, 1.0);
    var projX = x1 + clamped * dx;
    var projY = y1 + clamped * dy;
    // Inline function 'kotlin.math.pow' call
    var this_2 = px - projX;
    var tmp_0 = Math.pow(this_2, 2);
    // Inline function 'kotlin.math.pow' call
    var this_3 = py - projY;
    // Inline function 'kotlin.math.sqrt' call
    var x_0 = tmp_0 + Math.pow(this_3, 2);
    return Math.sqrt(x_0);
  }
  function sampleStrokePath(stroke, numSamples) {
    numSamples = numSamples === VOID ? 20 : numSamples;
    // Inline function 'kotlin.collections.mutableListOf' call
    var points = ArrayList_init_$Create$_0();
    var inductionVariable = 0;
    if (inductionVariable <= numSamples)
      $l$loop_0: do {
        var i = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var t = i / numSamples;
        switch (stroke.type.y1_1) {
          case 0:
            points.q(new DrawingPoint(stroke.startX + (stroke.endX - stroke.startX) * t, stroke.startY + (stroke.endY - stroke.startY) * t));
            break;
          case 1:
            var tmp1_elvis_lhs = stroke.controlX;
            var tmp;
            if (tmp1_elvis_lhs == null) {
              continue $l$loop_0;
            } else {
              tmp = tmp1_elvis_lhs;
            }

            var cx = tmp;
            var tmp2_elvis_lhs = stroke.controlY;
            var tmp_0;
            if (tmp2_elvis_lhs == null) {
              continue $l$loop_0;
            } else {
              tmp_0 = tmp2_elvis_lhs;
            }

            var cy = tmp_0;
            var mt = 1.0 - t;
            points.q(new DrawingPoint(mt * mt * stroke.startX + 2.0 * mt * t * cx + t * t * stroke.endX, mt * mt * stroke.startY + 2.0 * mt * t * cy + t * t * stroke.endY));
            break;
          case 2:
            var centerX = stroke.startX;
            var centerY = (stroke.startY + stroke.endY) / 2.0;
            var radiusY = (stroke.endY - stroke.startY) / 2.0;
            var tmp3_elvis_lhs = stroke.controlX;
            var radiusX = tmp3_elvis_lhs == null ? radiusY : tmp3_elvis_lhs;
            var angle = -3.141592653589793 / 2.0 + t * 3.141592653589793 * 2.0;
            // Inline function 'kotlin.math.cos' call

            var tmp_1 = centerX + radiusX * Math.cos(angle);
            // Inline function 'kotlin.math.sin' call

            var tmp$ret$2 = Math.sin(angle);
            points.q(new DrawingPoint(tmp_1, centerY + radiusY * tmp$ret$2));
            break;
          default:
            noWhenBranchMatchedException();
            break;
        }
      }
       while (!(i === numSamples));
    return points;
  }
  function validateStroke(userPoints, stroke, canvasSize) {
    if (userPoints.i() < 4)
      return false;
    var scale = canvasSize / 100.0;
    var expected = sampleStrokePath(stroke, 30);
    var userStart = first(userPoints);
    var userEnd = last(userPoints);
    var startDist = distance(new DrawingPoint(userStart.x / scale, userStart.y / scale), new DrawingPoint(stroke.startX, stroke.startY));
    var endDist = distance(new DrawingPoint(userEnd.x / scale, userEnd.y / scale), new DrawingPoint(stroke.endX, stroke.endY));
    if (startDist > 25.0 && endDist > 25.0)
      return false;
    var coveredCount = 0;
    var _iterator__ex2g4s = expected.f();
    while (_iterator__ex2g4s.g()) {
      var ep = _iterator__ex2g4s.h();
      var epScaled = new DrawingPoint(ep.x * scale, ep.y * scale);
      var minDist = 1.7976931348623157E308;
      var _iterator__ex2g4s_0 = userPoints.f();
      while (_iterator__ex2g4s_0.g()) {
        var up = _iterator__ex2g4s_0.h();
        var d = distance(epScaled, up);
        if (d < minDist)
          minDist = d;
      }
      if (minDist < 15.0 * scale * 0.01 * canvasSize * 0.01 + 18.0) {
        coveredCount = coveredCount + 1 | 0;
      }
    }
    var coverage = coveredCount / expected.i();
    return coverage >= 0.7;
  }
  function HangulRecognizer() {
    this.matchThreshold = 40;
  }
  protoOf(HangulRecognizer).zc = function (_set____db54di) {
    this.matchThreshold = _set____db54di;
  };
  protoOf(HangulRecognizer).ad = function () {
    return this.matchThreshold;
  };
  protoOf(HangulRecognizer).recognizeFromArrays = function (paths) {
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(paths.length);
    var inductionVariable = 0;
    var last = paths.length;
    while (inductionVariable < last) {
      var item = paths[inductionVariable];
      inductionVariable = inductionVariable + 1 | 0;
      var tmp$ret$2 = toList(item);
      destination.q(tmp$ret$2);
    }
    // Inline function 'kotlin.collections.toTypedArray' call
    var this_0 = this.recognize(destination);
    return copyToArray(this_0);
  };
  protoOf(HangulRecognizer).recognize = function (paths) {
    return recognizeShape(paths, true);
  };
  protoOf(HangulRecognizer).isMatchFromArrays = function (paths, expected) {
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(paths.length);
    var inductionVariable = 0;
    var last = paths.length;
    while (inductionVariable < last) {
      var item = paths[inductionVariable];
      inductionVariable = inductionVariable + 1 | 0;
      var tmp$ret$2 = toList(item);
      destination.q(tmp$ret$2);
    }
    return this.isMatch(destination, expected);
  };
  protoOf(HangulRecognizer).isMatch = function (paths, expected) {
    var results = this.recognize(paths);
    var tmp0_elvis_lhs = firstOrNull(results);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return false;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var top = tmp;
    return top.letter.equals(expected) && top.coverage >= this.matchThreshold;
  };
  protoOf(HangulRecognizer).validateGuidedStrokeFromArray = function (userPoints, letter, strokeIndex, canvasSize) {
    return this.validateGuidedStroke(toList(userPoints), letter, strokeIndex, canvasSize);
  };
  protoOf(HangulRecognizer).validateGuidedStroke = function (userPoints, letter, strokeIndex, canvasSize) {
    var tmp0_elvis_lhs = ReferenceData_getInstance().bd_1.u1(letter);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return false;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var strokes = tmp;
    if (!(0 <= strokeIndex ? strokeIndex <= (strokes.i() - 1 | 0) : false))
      return false;
    return validateStroke(userPoints, strokes.o(strokeIndex), canvasSize);
  };
  protoOf(HangulRecognizer).getReferenceStrokes = function (letter) {
    var tmp0_safe_receiver = ReferenceData_getInstance().bd_1.u1(letter);
    var tmp;
    if (tmp0_safe_receiver == null) {
      tmp = null;
    } else {
      // Inline function 'kotlin.collections.toTypedArray' call
      tmp = copyToArray(tmp0_safe_receiver);
    }
    return tmp;
  };
  protoOf(HangulRecognizer).getAllLetters = function () {
    // Inline function 'kotlin.collections.toTypedArray' call
    var this_0 = ReferenceData_getInstance().cd_1;
    return copyToArray(this_0);
  };
  protoOf(HangulRecognizer).dd = function (stroke, numSamples) {
    // Inline function 'kotlin.collections.toTypedArray' call
    var this_0 = sampleStrokePath(stroke, numSamples);
    return copyToArray(this_0);
  };
  protoOf(HangulRecognizer).sampleReferenceStroke = function (stroke, numSamples, $super) {
    numSamples = numSamples === VOID ? 40 : numSamples;
    return $super === VOID ? this.dd(stroke, numSamples) : $super.dd.call(this, stroke, numSamples);
  };
  function detectCircularPattern(segments, bbox) {
    if (segments.n())
      return false;
    var allPoints = flatten(segments);
    if (allPoints.i() < 10)
      return false;
    var tmp0 = bbox.hc();
    // Inline function 'kotlin.comparisons.maxOf' call
    var b = bbox.ic();
    // Inline function 'kotlin.let' call
    var it = Math.max(tmp0, b);
    var scale = it === 0.0 ? 1.0 : it;
    if (segments.i() > 1) {
      var gapCount = 0;
      var totalGapDistance = 0.0;
      var maxGapThreshold = scale * 0.15;
      var inductionVariable = 0;
      var last_0 = segments.i() - 1 | 0;
      if (inductionVariable < last_0)
        do {
          var i = inductionVariable;
          inductionVariable = inductionVariable + 1 | 0;
          var currentEnd = last(segments.o(i));
          var nextStart = first(segments.o(i + 1 | 0));
          var gapDist = distance(currentEnd, nextStart);
          if (gapDist > scale * 0.02) {
            gapCount = gapCount + 1 | 0;
            totalGapDistance = totalGapDistance + gapDist;
            if (gapDist > maxGapThreshold)
              return false;
          }
        }
         while (inductionVariable < last_0);
      if (gapCount > 3)
        return false;
      var circumference = 3.141592653589793 * scale;
      if (totalGapDistance > circumference * 0.25)
        return false;
    }
    var firstPoint = first(first(segments));
    var lastPoint = last(last(segments));
    var closureThreshold = scale * 0.2;
    var closureDist = distance(firstPoint, lastPoint);
    if (closureDist > closureThreshold)
      return false;
    var tmp = bbox.hc();
    // Inline function 'kotlin.let' call
    var it_0 = bbox.ic();
    var aspectRatio = tmp / (it_0 === 0.0 ? 1.0 : it_0);
    if (aspectRatio < 0.5 || aspectRatio > 2.0)
      return false;
    var centerX = bbox.dc_1 + bbox.hc() / 2.0;
    var centerY = bbox.ec_1 + bbox.ic() / 2.0;
    var avgRadius = (bbox.hc() + bbox.ic()) / 4.0;
    var radiusVariance = 0.0;
    var _iterator__ex2g4s = allPoints.f();
    while (_iterator__ex2g4s.g()) {
      var p = _iterator__ex2g4s.h();
      // Inline function 'kotlin.math.pow' call
      var this_0 = p.x - centerX;
      var tmp_0 = Math.pow(this_0, 2);
      // Inline function 'kotlin.math.pow' call
      var this_1 = p.y - centerY;
      // Inline function 'kotlin.math.sqrt' call
      var x = tmp_0 + Math.pow(this_1, 2);
      var dist = Math.sqrt(x);
      var tmp_1 = radiusVariance;
      // Inline function 'kotlin.math.abs' call
      var x_0 = dist - avgRadius;
      radiusVariance = tmp_1 + Math.abs(x_0);
    }
    radiusVariance = radiusVariance / allPoints.i();
    // Inline function 'kotlin.comparisons.maxOf' call
    var b_0 = allPoints.i() / 60 | 0;
    var step = Math.max(1, b_0);
    var cornerCount = 0;
    var straightSectionLength = 0;
    var maxStraightSection = 0;
    var i_0 = imul(step, 2);
    while (i_0 < allPoints.i()) {
      var p1 = allPoints.o(i_0 - imul(step, 2) | 0);
      var p2 = allPoints.o(i_0 - step | 0);
      var p3 = allPoints.o(i_0);
      var dx1 = p2.x - p1.x;
      var dy1 = p2.y - p1.y;
      var dx2 = p3.x - p2.x;
      var dy2 = p3.y - p2.y;
      // Inline function 'kotlin.math.sqrt' call
      var x_1 = dx1 * dx1 + dy1 * dy1;
      var len1 = Math.sqrt(x_1);
      // Inline function 'kotlin.math.sqrt' call
      var x_2 = dx2 * dx2 + dy2 * dy2;
      var len2 = Math.sqrt(x_2);
      if (len1 > 0.5 && len2 > 0.5) {
        var dot = (dx1 * dx2 + dy1 * dy2) / (len1 * len2);
        // Inline function 'kotlin.math.acos' call
        var x_3 = coerceIn(dot, -1.0, 1.0);
        var angle = Math.acos(x_3) * (180.0 / 3.141592653589793);
        if (angle < 10.0) {
          straightSectionLength = straightSectionLength + 1 | 0;
          var tmp0_0 = maxStraightSection;
          // Inline function 'kotlin.comparisons.maxOf' call
          var b_1 = straightSectionLength;
          maxStraightSection = Math.max(tmp0_0, b_1);
        } else {
          straightSectionLength = 0;
          if (angle > 35.0) {
            cornerCount = cornerCount + 1 | 0;
          }
        }
      }
      i_0 = i_0 + step | 0;
    }
    var sampledCount = allPoints.i() / step | 0;
    var straightRatio = sampledCount > 0 ? maxStraightSection / sampledCount : 0.0;
    if (cornerCount >= 3)
      return false;
    if (cornerCount > 0 && straightRatio > 0.15)
      return false;
    return radiusVariance < avgRadius * 0.4;
  }
  function splitPathAtCorners(points, angleThreshold) {
    angleThreshold = angleThreshold === VOID ? 45.0 : angleThreshold;
    if (points.i() < 3)
      return listOf_0(points);
    // Inline function 'kotlin.collections.mutableListOf' call
    var segments = ArrayList_init_$Create$_0();
    var currentSegment = mutableListOf([points.o(0)]);
    var cumulativeAngle = 0.0;
    var inductionVariable = 1;
    var last_0 = points.i() - 1 | 0;
    if (inductionVariable < last_0)
      do {
        var i = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        currentSegment.q(points.o(i));
        var inDirX = 0.0;
        var inDirY = 0.0;
        // Inline function 'kotlin.comparisons.maxOf' call
        var b = i - 3 | 0;
        var inStart = Math.max(0, b);
        var inductionVariable_0 = inStart;
        if (inductionVariable_0 < i)
          do {
            var j = inductionVariable_0;
            inductionVariable_0 = inductionVariable_0 + 1 | 0;
            inDirX = inDirX + (points.o(j + 1 | 0).x - points.o(j).x);
            inDirY = inDirY + (points.o(j + 1 | 0).y - points.o(j).y);
          }
           while (inductionVariable_0 < i);
        // Inline function 'kotlin.math.sqrt' call
        var x = inDirX * inDirX + inDirY * inDirY;
        var inLen = Math.sqrt(x);
        var outDirX = 0.0;
        var outDirY = 0.0;
        var tmp0 = points.i() - 1 | 0;
        // Inline function 'kotlin.comparisons.minOf' call
        var b_0 = i + 3 | 0;
        var outEnd = Math.min(tmp0, b_0);
        var inductionVariable_1 = i;
        if (inductionVariable_1 < outEnd)
          do {
            var j_0 = inductionVariable_1;
            inductionVariable_1 = inductionVariable_1 + 1 | 0;
            outDirX = outDirX + (points.o(j_0 + 1 | 0).x - points.o(j_0).x);
            outDirY = outDirY + (points.o(j_0 + 1 | 0).y - points.o(j_0).y);
          }
           while (inductionVariable_1 < outEnd);
        // Inline function 'kotlin.math.sqrt' call
        var x_0 = outDirX * outDirX + outDirY * outDirY;
        var outLen = Math.sqrt(x_0);
        if (inLen > 0.001 && outLen > 0.001) {
          var dot = (inDirX * outDirX + inDirY * outDirY) / (inLen * outLen);
          // Inline function 'kotlin.math.acos' call
          var x_1 = coerceIn(dot, -1.0, 1.0);
          var angle = Math.acos(x_1) * (180.0 / 3.141592653589793);
          cumulativeAngle = cumulativeAngle + angle;
          if (angle > angleThreshold || cumulativeAngle > 60.0) {
            segments.q(toList_0(currentSegment));
            currentSegment.k3();
            currentSegment.q(points.o(i));
            cumulativeAngle = 0.0;
          }
        }
      }
       while (inductionVariable < last_0);
    currentSegment.q(last(points));
    segments.q(toList_0(currentSegment));
    // Inline function 'kotlin.collections.filter' call
    // Inline function 'kotlin.collections.filterTo' call
    var destination = ArrayList_init_$Create$_0();
    var _iterator__ex2g4s = segments.f();
    while (_iterator__ex2g4s.g()) {
      var element = _iterator__ex2g4s.h();
      var tmp$ret$8;
      $l$block: {
        if (element.i() < 2) {
          tmp$ret$8 = false;
          break $l$block;
        }
        var dx = last(element).x - first(element).x;
        var dy = last(element).y - first(element).y;
        // Inline function 'kotlin.math.sqrt' call
        var x_2 = dx * dx + dy * dy;
        tmp$ret$8 = Math.sqrt(x_2) > 3.0;
      }
      if (tmp$ret$8) {
        destination.q(element);
      }
    }
    return destination;
  }
  function splitPathAtDirectionFlips(points, windowSize) {
    windowSize = windowSize === VOID ? 4 : windowSize;
    if (points.i() < 6)
      return listOf_0(points);
    // Inline function 'kotlin.collections.mutableListOf' call
    var segments = ArrayList_init_$Create$_0();
    var currentSegment = mutableListOf([points.o(0)]);
    var prevDir = null;
    var dirStableCount = 0;
    var inductionVariable = 1;
    var last_0 = points.i();
    if (inductionVariable < last_0)
      $l$loop_0: do {
        var i = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        currentSegment.q(points.o(i));
        // Inline function 'kotlin.comparisons.maxOf' call
        var b = i - windowSize | 0;
        var wStart = Math.max(0, b);
        var dx = 0.0;
        var dy = 0.0;
        var inductionVariable_0 = wStart;
        // Inline function 'kotlin.comparisons.minOf' call
        var b_0 = points.i() - 1 | 0;
        var last_1 = Math.min(i, b_0);
        if (inductionVariable_0 < last_1)
          do {
            var j = inductionVariable_0;
            inductionVariable_0 = inductionVariable_0 + 1 | 0;
            dx = dx + (points.o(j + 1 | 0).x - points.o(j).x);
            dy = dy + (points.o(j + 1 | 0).y - points.o(j).y);
          }
           while (inductionVariable_0 < last_1);
        // Inline function 'kotlin.math.abs' call
        var x = dx;
        var absDx = Math.abs(x);
        // Inline function 'kotlin.math.abs' call
        var x_0 = dy;
        var absDy = Math.abs(x_0);
        if (absDx < 0.5 && absDy < 0.5)
          continue $l$loop_0;
        // Inline function 'kotlin.comparisons.maxOf' call
        var dominance = Math.max(absDx, absDy) / (absDx + absDy);
        if (dominance < 0.85)
          continue $l$loop_0;
        var dir = absDx >= absDy ? _Char___init__impl__6a9atx(72) : _Char___init__impl__6a9atx(86);
        var tmp = prevDir;
        if (equals(new Char(dir), tmp == null ? null : new Char(tmp))) {
          dirStableCount = dirStableCount + 1 | 0;
        } else {
          var tmp_0 = prevDir;
          if (!((tmp_0 == null ? null : new Char(tmp_0)) == null)) {
            if (dirStableCount >= 8) {
              var newSegment = dropLast_0(currentSegment, 1);
              // Inline function 'kotlin.collections.isNotEmpty' call
              if (!newSegment.n()) {
                segments.q(newSegment);
              }
              currentSegment.k3();
              currentSegment.q(points.o(i));
            }
            dirStableCount = 0;
          }
        }
        prevDir = dir;
      }
       while (inductionVariable < last_0);
    if (currentSegment.i() >= 2) {
      segments.q(toList_0(currentSegment));
    }
    // Inline function 'kotlin.collections.filter' call
    // Inline function 'kotlin.collections.filterTo' call
    var destination = ArrayList_init_$Create$_0();
    var _iterator__ex2g4s = segments.f();
    while (_iterator__ex2g4s.g()) {
      var element = _iterator__ex2g4s.h();
      var tmp$ret$9;
      $l$block: {
        if (element.i() < 2) {
          tmp$ret$9 = false;
          break $l$block;
        }
        var dx_0 = last(element).x - first(element).x;
        var dy_0 = last(element).y - first(element).y;
        // Inline function 'kotlin.math.sqrt' call
        var x_1 = dx_0 * dx_0 + dy_0 * dy_0;
        tmp$ret$9 = Math.sqrt(x_1) > 2.0;
      }
      if (tmp$ret$9) {
        destination.q(element);
      }
    }
    return destination;
  }
  function mergeConnectedSegments(segments, bbox) {
    if (segments.i() <= 1)
      return segments;
    var tmp0 = bbox.hc();
    // Inline function 'kotlin.comparisons.maxOf' call
    var b = bbox.ic();
    // Inline function 'kotlin.let' call
    var it = Math.max(tmp0, b);
    var scale = it === 0.0 ? 1.0 : it;
    var connectionThreshold = scale * 0.1;
    // Inline function 'kotlin.collections.mutableListOf' call
    var merged = ArrayList_init_$Create$_0();
    var current = toMutableList(segments.o(0));
    var inductionVariable = 1;
    var last_0 = segments.i();
    if (inductionVariable < last_0)
      $l$loop: do {
        var i = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var next = segments.o(i);
        if (next.n())
          continue $l$loop;
        var currentEnd = last(current);
        var nextStart = first(next);
        var dist = distance(currentEnd, nextStart);
        var tmp = current;
        // Inline function 'kotlin.comparisons.maxOf' call
        var b_0 = current.i() - 3 | 0;
        var tmp$ret$4 = Math.max(0, b_0);
        var tmp0_0 = currentEnd.y - tmp.o(tmp$ret$4).y;
        var tmp_0 = current;
        // Inline function 'kotlin.comparisons.maxOf' call
        var b_1 = current.i() - 3 | 0;
        var tmp$ret$5 = Math.max(0, b_1);
        // Inline function 'kotlin.math.atan2' call
        var x = currentEnd.x - tmp_0.o(tmp$ret$5).x;
        var currentDir = Math.atan2(tmp0_0, x);
        // Inline function 'kotlin.comparisons.minOf' call
        var b_2 = next.i() - 1 | 0;
        var tmp$ret$7 = Math.min(2, b_2);
        var tmp0_1 = next.o(tmp$ret$7).y - nextStart.y;
        // Inline function 'kotlin.comparisons.minOf' call
        var b_3 = next.i() - 1 | 0;
        var tmp$ret$8 = Math.min(2, b_3);
        // Inline function 'kotlin.math.atan2' call
        var x_0 = next.o(tmp$ret$8).x - nextStart.x;
        var nextDir = Math.atan2(tmp0_1, x_0);
        // Inline function 'kotlin.math.abs' call
        var x_1 = currentDir - nextDir;
        var angleDiff = Math.abs(x_1);
        // Inline function 'kotlin.comparisons.minOf' call
        var b_4 = 3.141592653589793 * 2.0 - angleDiff;
        var normalizedAngleDiff = Math.min(angleDiff, b_4);
        if (dist < connectionThreshold && normalizedAngleDiff < 1.0471975511965976) {
          current.m(next);
        } else {
          merged.q(current);
          current = toMutableList(next);
        }
      }
       while (inductionVariable < last_0);
    merged.q(current);
    return merged;
  }
  function deduplicateOverlappingSegments(segments, bbox) {
    if (segments.i() <= 1)
      return segments;
    var tmp0 = bbox.hc();
    // Inline function 'kotlin.comparisons.maxOf' call
    var b = bbox.ic();
    // Inline function 'kotlin.let' call
    var it = Math.max(tmp0, b);
    var scale = it === 0.0 ? 1.0 : it;
    var overlapThreshold = scale * 0.08;
    // Inline function 'kotlin.collections.map' call
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList_init_$Create$(collectionSizeOrDefault(segments, 10));
    var _iterator__ex2g4s = segments.f();
    while (_iterator__ex2g4s.g()) {
      var item = _iterator__ex2g4s.h();
      var start = first(item);
      var end = last(item);
      var dx = end.x - start.x;
      var dy = end.y - start.y;
      // Inline function 'kotlin.math.sqrt' call
      var x = dx * dx + dy * dy;
      var tmp = Math.sqrt(x);
      // Inline function 'kotlin.math.atan2' call
      var tmp$ret$7 = Math.atan2(dy, dx);
      var tmp$ret$5 = new deduplicateOverlappingSegments$SegmentProps(start, end, tmp, tmp$ret$7, (start.x + end.x) / 2.0, (start.y + end.y) / 2.0);
      destination.q(tmp$ret$5);
    }
    var props = destination;
    // Inline function 'kotlin.collections.mutableSetOf' call
    var toRemove = LinkedHashSet_init_$Create$();
    var inductionVariable = 0;
    var last_0 = props.i() - 1 | 0;
    if (inductionVariable <= last_0)
      $l$loop: do {
        var i = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (toRemove.k1(i))
          continue $l$loop;
        var inductionVariable_0 = i + 1 | 0;
        var last_1 = props.i();
        if (inductionVariable_0 < last_1)
          $l$loop_3: do {
            var j = inductionVariable_0;
            inductionVariable_0 = inductionVariable_0 + 1 | 0;
            if (toRemove.k1(j))
              continue $l$loop_3;
            var a = props.o(i);
            var b_0 = props.o(j);
            // Inline function 'kotlin.math.abs' call
            var x_0 = a.hd_1 - b_0.hd_1;
            var angleDiff = Math.abs(x_0);
            if (angleDiff > 3.141592653589793)
              angleDiff = 2.0 * 3.141592653589793 - angleDiff;
            // Inline function 'kotlin.math.abs' call
            var x_1 = a.hd_1 - b_0.hd_1 + 3.141592653589793;
            var antiAngleDiff = Math.abs(x_1);
            if (antiAngleDiff > 3.141592653589793)
              antiAngleDiff = 2.0 * 3.141592653589793 - antiAngleDiff;
            if (angleDiff >= 0.4 && antiAngleDiff >= 0.4)
              continue $l$loop_3;
            var perpDistA = pointToLineDistance(a.id_1, a.jd_1, b_0.ed_1.x, b_0.ed_1.y, b_0.fd_1.x, b_0.fd_1.y);
            var perpDistB = pointToLineDistance(b_0.id_1, b_0.jd_1, a.ed_1.x, a.ed_1.y, a.fd_1.x, a.fd_1.y);
            if (perpDistA >= overlapThreshold || perpDistB >= overlapThreshold)
              continue $l$loop_3;
            var lineDir = a.gd_1 > b_0.gd_1 ? a : b_0;
            // Inline function 'kotlin.math.cos' call
            var x_2 = lineDir.hd_1;
            var cosAngle = Math.cos(x_2);
            // Inline function 'kotlin.math.sin' call
            var x_3 = lineDir.hd_1;
            var sinAngle = Math.sin(x_3);
            var projA1 = a.ed_1.x * cosAngle + a.ed_1.y * sinAngle;
            var projA2 = a.fd_1.x * cosAngle + a.fd_1.y * sinAngle;
            var projB1 = b_0.ed_1.x * cosAngle + b_0.ed_1.y * sinAngle;
            var projB2 = b_0.fd_1.x * cosAngle + b_0.fd_1.y * sinAngle;
            // Inline function 'kotlin.comparisons.minOf' call
            var aMin = Math.min(projA1, projA2);
            // Inline function 'kotlin.comparisons.maxOf' call
            var aMax = Math.max(projA1, projA2);
            // Inline function 'kotlin.comparisons.minOf' call
            var bMin = Math.min(projB1, projB2);
            // Inline function 'kotlin.comparisons.maxOf' call
            var bMax = Math.max(projB1, projB2);
            // Inline function 'kotlin.comparisons.minOf' call
            var tmp_0 = Math.min(aMax, bMax);
            // Inline function 'kotlin.comparisons.maxOf' call
            // Inline function 'kotlin.comparisons.maxOf' call
            var b_1 = tmp_0 - Math.max(aMin, bMin);
            var overlapLength = Math.max(0.0, b_1);
            var tmp0_0 = a.gd_1;
            // Inline function 'kotlin.comparisons.minOf' call
            var b_2 = b_0.gd_1;
            var minLength = Math.min(tmp0_0, b_2);
            var overlapRatio = minLength > 0 ? overlapLength / minLength : 0.0;
            if (overlapRatio > 0.5) {
              if (a.gd_1 >= b_0.gd_1) {
                toRemove.q(j);
              } else {
                toRemove.q(i);
                break $l$loop_3;
              }
            }
          }
           while (inductionVariable_0 < last_1);
      }
       while (inductionVariable <= last_0);
    // Inline function 'kotlin.collections.filterIndexed' call
    // Inline function 'kotlin.collections.filterIndexedTo' call
    var destination_0 = ArrayList_init_$Create$_0();
    // Inline function 'kotlin.collections.forEachIndexed' call
    var index = 0;
    var _iterator__ex2g4s_0 = segments.f();
    while (_iterator__ex2g4s_0.g()) {
      var item_0 = _iterator__ex2g4s_0.h();
      var _unary__edvuaz = index;
      index = _unary__edvuaz + 1 | 0;
      var idx = checkIndexOverflow(_unary__edvuaz);
      if (!toRemove.k1(idx)) {
        destination_0.q(item_0);
      }
    }
    return destination_0;
  }
  function deduplicateOverlappingSegments$SegmentProps(start, end, length, angle, midX, midY) {
    this.ed_1 = start;
    this.fd_1 = end;
    this.gd_1 = length;
    this.hd_1 = angle;
    this.id_1 = midX;
    this.jd_1 = midY;
  }
  protoOf(deduplicateOverlappingSegments$SegmentProps).toString = function () {
    return 'SegmentProps(start=' + this.ed_1.toString() + ', end=' + this.fd_1.toString() + ', length=' + this.gd_1 + ', angle=' + this.hd_1 + ', midX=' + this.id_1 + ', midY=' + this.jd_1 + ')';
  };
  protoOf(deduplicateOverlappingSegments$SegmentProps).hashCode = function () {
    var result = this.ed_1.hashCode();
    result = imul(result, 31) + this.fd_1.hashCode() | 0;
    result = imul(result, 31) + getNumberHashCode(this.gd_1) | 0;
    result = imul(result, 31) + getNumberHashCode(this.hd_1) | 0;
    result = imul(result, 31) + getNumberHashCode(this.id_1) | 0;
    result = imul(result, 31) + getNumberHashCode(this.jd_1) | 0;
    return result;
  };
  protoOf(deduplicateOverlappingSegments$SegmentProps).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof deduplicateOverlappingSegments$SegmentProps))
      return false;
    if (!this.ed_1.equals(other.ed_1))
      return false;
    if (!this.fd_1.equals(other.fd_1))
      return false;
    if (!equals(this.gd_1, other.gd_1))
      return false;
    if (!equals(this.hd_1, other.hd_1))
      return false;
    if (!equals(this.id_1, other.id_1))
      return false;
    if (!equals(this.jd_1, other.jd_1))
      return false;
    return true;
  };
  function ReferenceData() {
    ReferenceData_instance = this;
    this.bd_1 = mapOf([to(Letters_getInstance().ㄱ, listOf([new ReferenceStroke(20.0, 25.0, 80.0, 25.0, StrokeType_LINE_getInstance()), new ReferenceStroke(80.0, 25.0, 80.0, 80.0, StrokeType_LINE_getInstance())])), to(Letters_getInstance().ㄴ, listOf([new ReferenceStroke(20.0, 20.0, 20.0, 80.0, StrokeType_LINE_getInstance()), new ReferenceStroke(20.0, 80.0, 80.0, 80.0, StrokeType_LINE_getInstance())])), to(Letters_getInstance().ㄷ, listOf([new ReferenceStroke(20.0, 20.0, 80.0, 20.0, StrokeType_LINE_getInstance()), new ReferenceStroke(20.0, 20.0, 20.0, 80.0, StrokeType_LINE_getInstance()), new ReferenceStroke(20.0, 80.0, 80.0, 80.0, StrokeType_LINE_getInstance())])), to(Letters_getInstance().ㄹ, listOf([new ReferenceStroke(15.0, 25.0, 85.0, 25.0, StrokeType_LINE_getInstance()), new ReferenceStroke(85.0, 25.0, 85.0, 48.0, StrokeType_LINE_getInstance()), new ReferenceStroke(85.0, 48.0, 15.0, 48.0, StrokeType_LINE_getInstance()), new ReferenceStroke(15.0, 48.0, 15.0, 72.0, StrokeType_LINE_getInstance()), new ReferenceStroke(15.0, 72.0, 85.0, 72.0, StrokeType_LINE_getInstance())])), to(Letters_getInstance().ㅁ, listOf([new ReferenceStroke(20.0, 28.0, 20.0, 72.0, StrokeType_LINE_getInstance()), new ReferenceStroke(20.0, 72.0, 80.0, 72.0, StrokeType_LINE_getInstance()), new ReferenceStroke(80.0, 72.0, 80.0, 28.0, StrokeType_LINE_getInstance()), new ReferenceStroke(80.0, 28.0, 20.0, 28.0, StrokeType_LINE_getInstance())])), to(Letters_getInstance().ㅂ, listOf([new ReferenceStroke(25.0, 25.0, 25.0, 75.0, StrokeType_LINE_getInstance()), new ReferenceStroke(75.0, 25.0, 75.0, 75.0, StrokeType_LINE_getInstance()), new ReferenceStroke(25.0, 50.0, 75.0, 50.0, StrokeType_LINE_getInstance()), new ReferenceStroke(25.0, 75.0, 75.0, 75.0, StrokeType_LINE_getInstance())])), to(Letters_getInstance().ㅅ, listOf([new ReferenceStroke(50.0, 20.0, 15.0, 80.0, StrokeType_CURVE_getInstance(), 45.0, 55.0), new ReferenceStroke(50.0, 20.0, 85.0, 80.0, StrokeType_CURVE_getInstance(), 55.0, 55.0)])), to(Letters_getInstance().ㅇ, listOf_0(new ReferenceStroke(50.0, 20.0, 50.0, 80.0, StrokeType_CIRCLE_getInstance()))), to(Letters_getInstance().ㅈ, listOf([new ReferenceStroke(20.0, 20.0, 80.0, 20.0, StrokeType_LINE_getInstance()), new ReferenceStroke(50.0, 20.0, 18.0, 80.0, StrokeType_CURVE_getInstance(), 45.0, 52.0), new ReferenceStroke(50.0, 20.0, 82.0, 80.0, StrokeType_CURVE_getInstance(), 55.0, 52.0)])), to(Letters_getInstance().ㅊ, listOf([new ReferenceStroke(50.0, 12.0, 50.0, 30.0, StrokeType_LINE_getInstance()), new ReferenceStroke(20.0, 30.0, 80.0, 30.0, StrokeType_LINE_getInstance()), new ReferenceStroke(50.0, 30.0, 18.0, 85.0, StrokeType_CURVE_getInstance(), 45.0, 58.0), new ReferenceStroke(50.0, 30.0, 82.0, 85.0, StrokeType_CURVE_getInstance(), 55.0, 58.0)])), to(Letters_getInstance().ㅋ, listOf([new ReferenceStroke(25.0, 25.0, 75.0, 25.0, StrokeType_LINE_getInstance()), new ReferenceStroke(75.0, 25.0, 75.0, 75.0, StrokeType_LINE_getInstance()), new ReferenceStroke(25.0, 50.0, 75.0, 50.0, StrokeType_LINE_getInstance())])), to(Letters_getInstance().ㅌ, listOf([new ReferenceStroke(20.0, 25.0, 80.0, 25.0, StrokeType_LINE_getInstance()), new ReferenceStroke(20.0, 25.0, 20.0, 75.0, StrokeType_LINE_getInstance()), new ReferenceStroke(20.0, 50.0, 80.0, 50.0, StrokeType_LINE_getInstance()), new ReferenceStroke(20.0, 75.0, 80.0, 75.0, StrokeType_LINE_getInstance())])), to(Letters_getInstance().ㅍ, listOf([new ReferenceStroke(25.0, 28.0, 75.0, 28.0, StrokeType_LINE_getInstance()), new ReferenceStroke(35.0, 28.0, 35.0, 72.0, StrokeType_LINE_getInstance()), new ReferenceStroke(65.0, 28.0, 65.0, 72.0, StrokeType_LINE_getInstance()), new ReferenceStroke(25.0, 72.0, 75.0, 72.0, StrokeType_LINE_getInstance())])), to(Letters_getInstance().ㅎ, listOf([new ReferenceStroke(50.0, 15.0, 50.0, 30.0, StrokeType_LINE_getInstance()), new ReferenceStroke(25.0, 30.0, 75.0, 30.0, StrokeType_LINE_getInstance()), new ReferenceStroke(50.0, 38.0, 50.0, 78.0, StrokeType_CIRCLE_getInstance(), 25.0)])), to(Letters_getInstance().ㄲ, listOf([new ReferenceStroke(10.0, 25.0, 45.0, 25.0, StrokeType_LINE_getInstance()), new ReferenceStroke(45.0, 25.0, 45.0, 80.0, StrokeType_LINE_getInstance()), new ReferenceStroke(55.0, 25.0, 90.0, 25.0, StrokeType_LINE_getInstance()), new ReferenceStroke(90.0, 25.0, 90.0, 80.0, StrokeType_LINE_getInstance())])), to(Letters_getInstance().ㄸ, listOf([new ReferenceStroke(8.0, 20.0, 45.0, 20.0, StrokeType_LINE_getInstance()), new ReferenceStroke(8.0, 20.0, 8.0, 80.0, StrokeType_LINE_getInstance()), new ReferenceStroke(8.0, 80.0, 45.0, 80.0, StrokeType_LINE_getInstance()), new ReferenceStroke(55.0, 20.0, 92.0, 20.0, StrokeType_LINE_getInstance()), new ReferenceStroke(55.0, 20.0, 55.0, 80.0, StrokeType_LINE_getInstance()), new ReferenceStroke(55.0, 80.0, 92.0, 80.0, StrokeType_LINE_getInstance())])), to(Letters_getInstance().ㅃ, listOf([new ReferenceStroke(10.0, 20.0, 10.0, 80.0, StrokeType_LINE_getInstance()), new ReferenceStroke(42.0, 20.0, 42.0, 80.0, StrokeType_LINE_getInstance()), new ReferenceStroke(10.0, 50.0, 42.0, 50.0, StrokeType_LINE_getInstance()), new ReferenceStroke(10.0, 80.0, 42.0, 80.0, StrokeType_LINE_getInstance()), new ReferenceStroke(58.0, 20.0, 58.0, 80.0, StrokeType_LINE_getInstance()), new ReferenceStroke(90.0, 20.0, 90.0, 80.0, StrokeType_LINE_getInstance()), new ReferenceStroke(58.0, 50.0, 90.0, 50.0, StrokeType_LINE_getInstance()), new ReferenceStroke(58.0, 80.0, 90.0, 80.0, StrokeType_LINE_getInstance())])), to(Letters_getInstance().ㅆ, listOf([new ReferenceStroke(30.0, 20.0, 8.0, 80.0, StrokeType_CURVE_getInstance(), 26.0, 55.0), new ReferenceStroke(30.0, 20.0, 50.0, 80.0, StrokeType_CURVE_getInstance(), 34.0, 55.0), new ReferenceStroke(70.0, 20.0, 50.0, 80.0, StrokeType_CURVE_getInstance(), 66.0, 55.0), new ReferenceStroke(70.0, 20.0, 92.0, 80.0, StrokeType_CURVE_getInstance(), 74.0, 55.0)])), to(Letters_getInstance().ㅉ, listOf([new ReferenceStroke(8.0, 20.0, 45.0, 20.0, StrokeType_LINE_getInstance()), new ReferenceStroke(27.0, 20.0, 10.0, 80.0, StrokeType_CURVE_getInstance(), 24.0, 52.0), new ReferenceStroke(27.0, 20.0, 43.0, 80.0, StrokeType_CURVE_getInstance(), 30.0, 52.0), new ReferenceStroke(55.0, 20.0, 92.0, 20.0, StrokeType_LINE_getInstance()), new ReferenceStroke(74.0, 20.0, 57.0, 80.0, StrokeType_CURVE_getInstance(), 71.0, 52.0), new ReferenceStroke(74.0, 20.0, 90.0, 80.0, StrokeType_CURVE_getInstance(), 77.0, 52.0)])), to(Letters_getInstance().ㅏ, listOf([new ReferenceStroke(35.0, 10.0, 35.0, 90.0, StrokeType_LINE_getInstance()), new ReferenceStroke(35.0, 45.0, 55.0, 45.0, StrokeType_LINE_getInstance())])), to(Letters_getInstance().ㅑ, listOf([new ReferenceStroke(30.0, 10.0, 30.0, 90.0, StrokeType_LINE_getInstance()), new ReferenceStroke(30.0, 35.0, 50.0, 35.0, StrokeType_LINE_getInstance()), new ReferenceStroke(30.0, 60.0, 50.0, 60.0, StrokeType_LINE_getInstance())])), to(Letters_getInstance().ㅓ, listOf([new ReferenceStroke(65.0, 10.0, 65.0, 90.0, StrokeType_LINE_getInstance()), new ReferenceStroke(45.0, 45.0, 65.0, 45.0, StrokeType_LINE_getInstance())])), to(Letters_getInstance().ㅕ, listOf([new ReferenceStroke(70.0, 10.0, 70.0, 90.0, StrokeType_LINE_getInstance()), new ReferenceStroke(50.0, 35.0, 70.0, 35.0, StrokeType_LINE_getInstance()), new ReferenceStroke(50.0, 60.0, 70.0, 60.0, StrokeType_LINE_getInstance())])), to(Letters_getInstance().ㅗ, listOf([new ReferenceStroke(50.0, 37.0, 50.0, 55.0, StrokeType_LINE_getInstance()), new ReferenceStroke(10.0, 55.0, 90.0, 55.0, StrokeType_LINE_getInstance())])), to(Letters_getInstance().ㅛ, listOf([new ReferenceStroke(35.0, 37.0, 35.0, 55.0, StrokeType_LINE_getInstance()), new ReferenceStroke(65.0, 37.0, 65.0, 55.0, StrokeType_LINE_getInstance()), new ReferenceStroke(10.0, 55.0, 90.0, 55.0, StrokeType_LINE_getInstance())])), to(Letters_getInstance().ㅜ, listOf([new ReferenceStroke(10.0, 45.0, 90.0, 45.0, StrokeType_LINE_getInstance()), new ReferenceStroke(50.0, 45.0, 50.0, 63.0, StrokeType_LINE_getInstance())])), to(Letters_getInstance().ㅠ, listOf([new ReferenceStroke(15.0, 45.0, 85.0, 45.0, StrokeType_LINE_getInstance()), new ReferenceStroke(35.0, 45.0, 35.0, 66.0, StrokeType_LINE_getInstance()), new ReferenceStroke(65.0, 45.0, 65.0, 66.0, StrokeType_LINE_getInstance())])), to(Letters_getInstance().ㅡ, listOf_0(new ReferenceStroke(10.0, 50.0, 90.0, 50.0, StrokeType_LINE_getInstance()))), to(Letters_getInstance().ㅣ, listOf_0(new ReferenceStroke(50.0, 10.0, 50.0, 90.0, StrokeType_LINE_getInstance()))), to(Letters_getInstance().ㅐ, listOf([new ReferenceStroke(25.0, 10.0, 25.0, 90.0, StrokeType_LINE_getInstance()), new ReferenceStroke(25.0, 45.0, 60.0, 45.0, StrokeType_LINE_getInstance()), new ReferenceStroke(60.0, 10.0, 60.0, 90.0, StrokeType_LINE_getInstance())])), to(Letters_getInstance().ㅔ, listOf([new ReferenceStroke(40.0, 10.0, 40.0, 90.0, StrokeType_LINE_getInstance()), new ReferenceStroke(25.0, 45.0, 40.0, 45.0, StrokeType_LINE_getInstance()), new ReferenceStroke(60.0, 10.0, 60.0, 90.0, StrokeType_LINE_getInstance())])), to(Letters_getInstance().ㅒ, listOf([new ReferenceStroke(22.0, 10.0, 22.0, 90.0, StrokeType_LINE_getInstance()), new ReferenceStroke(22.0, 35.0, 58.0, 35.0, StrokeType_LINE_getInstance()), new ReferenceStroke(22.0, 60.0, 58.0, 60.0, StrokeType_LINE_getInstance()), new ReferenceStroke(58.0, 10.0, 58.0, 90.0, StrokeType_LINE_getInstance())])), to(Letters_getInstance().ㅖ, listOf([new ReferenceStroke(42.0, 10.0, 42.0, 90.0, StrokeType_LINE_getInstance()), new ReferenceStroke(22.0, 35.0, 42.0, 35.0, StrokeType_LINE_getInstance()), new ReferenceStroke(22.0, 60.0, 42.0, 60.0, StrokeType_LINE_getInstance()), new ReferenceStroke(62.0, 10.0, 62.0, 90.0, StrokeType_LINE_getInstance())])), to(Letters_getInstance().ㅘ, listOf([new ReferenceStroke(36.0, 51.0, 36.0, 64.0, StrokeType_LINE_getInstance()), new ReferenceStroke(16.0, 64.0, 56.0, 64.0, StrokeType_LINE_getInstance()), new ReferenceStroke(65.0, 10.0, 65.0, 90.0, StrokeType_LINE_getInstance()), new ReferenceStroke(65.0, 50.0, 85.0, 50.0, StrokeType_LINE_getInstance())])), to(Letters_getInstance().ㅝ, listOf([new ReferenceStroke(16.0, 50.0, 56.0, 50.0, StrokeType_LINE_getInstance()), new ReferenceStroke(36.0, 50.0, 36.0, 64.0, StrokeType_LINE_getInstance()), new ReferenceStroke(65.0, 10.0, 65.0, 90.0, StrokeType_LINE_getInstance()), new ReferenceStroke(50.0, 70.0, 65.0, 70.0, StrokeType_LINE_getInstance())])), to(Letters_getInstance().ㅙ, listOf([new ReferenceStroke(26.0, 51.0, 26.0, 64.0, StrokeType_LINE_getInstance()), new ReferenceStroke(8.0, 64.0, 44.0, 64.0, StrokeType_LINE_getInstance()), new ReferenceStroke(58.0, 10.0, 58.0, 90.0, StrokeType_LINE_getInstance()), new ReferenceStroke(58.0, 50.0, 85.0, 50.0, StrokeType_LINE_getInstance()), new ReferenceStroke(85.0, 10.0, 85.0, 90.0, StrokeType_LINE_getInstance())])), to(Letters_getInstance().ㅞ, listOf([new ReferenceStroke(12.0, 50.0, 50.0, 50.0, StrokeType_LINE_getInstance()), new ReferenceStroke(31.0, 50.0, 31.0, 64.0, StrokeType_LINE_getInstance()), new ReferenceStroke(45.0, 70.0, 60.0, 70.0, StrokeType_LINE_getInstance()), new ReferenceStroke(60.0, 10.0, 60.0, 90.0, StrokeType_LINE_getInstance()), new ReferenceStroke(78.0, 10.0, 78.0, 90.0, StrokeType_LINE_getInstance())])), to(Letters_getInstance().ㅚ, listOf([new ReferenceStroke(32.0, 51.0, 32.0, 64.0, StrokeType_LINE_getInstance()), new ReferenceStroke(12.0, 64.0, 52.0, 64.0, StrokeType_LINE_getInstance()), new ReferenceStroke(68.0, 10.0, 68.0, 90.0, StrokeType_LINE_getInstance())])), to(Letters_getInstance().ㅟ, listOf([new ReferenceStroke(16.0, 50.0, 56.0, 50.0, StrokeType_LINE_getInstance()), new ReferenceStroke(36.0, 50.0, 36.0, 64.0, StrokeType_LINE_getInstance()), new ReferenceStroke(68.0, 10.0, 68.0, 90.0, StrokeType_LINE_getInstance())])), to(Letters_getInstance().ㅢ, listOf([new ReferenceStroke(10.0, 58.0, 62.0, 58.0, StrokeType_LINE_getInstance()), new ReferenceStroke(66.0, 10.0, 66.0, 90.0, StrokeType_LINE_getInstance())]))]);
    this.cd_1 = toList_0(this.bd_1.v1());
  }
  var ReferenceData_instance;
  function ReferenceData_getInstance() {
    if (ReferenceData_instance == null)
      new ReferenceData();
    return ReferenceData_instance;
  }
  function ComparisonResult(score, debug) {
    this.kd_1 = score;
    this.ld_1 = debug;
  }
  protoOf(ComparisonResult).h7 = function () {
    return this.kd_1;
  };
  protoOf(ComparisonResult).i7 = function () {
    return this.ld_1;
  };
  protoOf(ComparisonResult).toString = function () {
    return 'ComparisonResult(score=' + this.kd_1 + ', debug=' + this.ld_1 + ')';
  };
  protoOf(ComparisonResult).hashCode = function () {
    var result = getNumberHashCode(this.kd_1);
    result = imul(result, 31) + getStringHashCode(this.ld_1) | 0;
    return result;
  };
  protoOf(ComparisonResult).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof ComparisonResult))
      return false;
    if (!equals(this.kd_1, other.kd_1))
      return false;
    if (!(this.ld_1 === other.ld_1))
      return false;
    return true;
  };
  function compareSignatures(user, ref) {
    var debug = StringBuilder_init_$Create$_0();
    var countDiff = abs(user.md_1 - ref.md_1 | 0);
    // Inline function 'kotlin.comparisons.maxOf' call
    var b = 1.0 - countDiff * 0.4;
    var countScore = Math.max(0.0, b);
    debug.f6('Strokes: ' + user.md_1 + '/' + ref.md_1 + ' | ');
    if (user.md_1 === 0 || ref.md_1 === 0) {
      return new ComparisonResult(0.0, debug.toString());
    }
    if (countDiff >= 2) {
      return new ComparisonResult(countScore * 0.5, debug.f6('Too different').toString());
    }
    var _destruct__k2r9zo = compareSignatures$typeDistribution(user.nd_1);
    var uh = _destruct__k2r9zo.h7();
    var uv = _destruct__k2r9zo.i7();
    var uc = _destruct__k2r9zo.w8();
    var _destruct__k2r9zo_0 = compareSignatures$typeDistribution(ref.nd_1);
    var rh = _destruct__k2r9zo_0.h7();
    var rv = _destruct__k2r9zo_0.i7();
    var rc = _destruct__k2r9zo_0.w8();
    var distMismatch = (abs(uh - rh | 0) + abs(uv - rv | 0) | 0) + abs(uc - rc | 0) | 0;
    // Inline function 'kotlin.comparisons.maxOf' call
    var b_0 = 1.0 - distMismatch * 0.25;
    var distScore = Math.max(0.0, b_0);
    // Inline function 'kotlin.collections.mutableListOf' call
    var mappings = ArrayList_init_$Create$_0();
    var inductionVariable = 0;
    var last = ref.nd_1.i() - 1 | 0;
    if (inductionVariable <= last)
      do {
        var r = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var inductionVariable_0 = 0;
        var last_0 = user.nd_1.i() - 1 | 0;
        if (inductionVariable_0 <= last_0)
          do {
            var u = inductionVariable_0;
            inductionVariable_0 = inductionVariable_0 + 1 | 0;
            var refStroke = ref.nd_1.o(r);
            var userStroke = user.nd_1.o(u);
            var dirScore = 0.0;
            if (refStroke.jc_1.equals(StrokeDirection_CIRCLE_getInstance()) || userStroke.jc_1.equals(StrokeDirection_CIRCLE_getInstance())) {
              dirScore = refStroke.jc_1.equals(userStroke.jc_1) ? 1.0 : 0.0;
            } else if (refStroke.jc_1.equals(userStroke.jc_1)) {
              var refAngle = (refStroke.rc_1 % 180.0 + 180.0) % 180.0;
              var userAngle = (userStroke.rc_1 % 180.0 + 180.0) % 180.0;
              // Inline function 'kotlin.math.abs' call
              var x = refAngle - userAngle;
              var angleDiff = Math.abs(x);
              if (angleDiff > 90.0)
                angleDiff = 180.0 - angleDiff;
              // Inline function 'kotlin.comparisons.maxOf' call
              var b_1 = 1.0 - angleDiff / 90.0 * 0.5;
              dirScore = Math.max(0.5, b_1);
            } else {
              var refAngle_0 = (refStroke.rc_1 % 180.0 + 180.0) % 180.0;
              var userAngle_0 = (userStroke.rc_1 % 180.0 + 180.0) % 180.0;
              // Inline function 'kotlin.math.abs' call
              var x_0 = refAngle_0 - userAngle_0;
              var angleDiff_0 = Math.abs(x_0);
              if (angleDiff_0 > 90.0)
                angleDiff_0 = 180.0 - angleDiff_0;
              dirScore = angleDiff_0 < 20.0 ? 0.3 : 0.0;
            }
            var posDist = distance(refStroke.mc_1, userStroke.mc_1);
            // Inline function 'kotlin.comparisons.maxOf' call
            var b_2 = 1.0 - posDist * 3.5;
            var posScore = Math.max(0.0, b_2);
            var matchScore = dirScore * 0.7 + posScore * 0.3;
            if (dirScore > 0 && matchScore > 0.3) {
              mappings.q(new compareSignatures$StrokeMapping(r, u, matchScore));
            }
          }
           while (inductionVariable_0 <= last_0);
      }
       while (inductionVariable <= last);
    // Inline function 'kotlin.collections.sortByDescending' call
    if (mappings.i() > 1) {
      // Inline function 'kotlin.comparisons.compareByDescending' call
      var tmp = compareSignatures$lambda;
      var tmp$ret$8 = new sam$kotlin_Comparator$0(tmp);
      sortWith(mappings, tmp$ret$8);
    }
    // Inline function 'kotlin.collections.mutableSetOf' call
    var usedRef = LinkedHashSet_init_$Create$();
    // Inline function 'kotlin.collections.mutableSetOf' call
    var usedUser = LinkedHashSet_init_$Create$();
    // Inline function 'kotlin.collections.mutableListOf' call
    var finalMapping = ArrayList_init_$Create$_0();
    var totalDirScore = 0.0;
    var _iterator__ex2g4s = mappings.f();
    while (_iterator__ex2g4s.g()) {
      var m = _iterator__ex2g4s.h();
      if (!usedRef.k1(m.pd_1) && !usedUser.k1(m.qd_1)) {
        usedRef.q(m.pd_1);
        usedUser.q(m.qd_1);
        finalMapping.q(m);
        totalDirScore = totalDirScore + m.rd_1;
      }
    }
    var unmappedUser = user.md_1 - usedUser.i() | 0;
    var unmappedRef = ref.md_1 - usedRef.i() | 0;
    var totalUnmapped = unmappedUser + unmappedRef | 0;
    var tmp0 = user.md_1;
    // Inline function 'kotlin.comparisons.maxOf' call
    var b_3 = ref.md_1;
    var maxStrokeCount = Math.max(tmp0, b_3);
    var directionScore = maxStrokeCount > 0 ? totalDirScore / maxStrokeCount : 0.0;
    // Inline function 'kotlin.comparisons.maxOf' call
    var b_4 = 1.0 - totalUnmapped * 0.3;
    var unmappedPenalty = Math.max(0.0, b_4);
    debug.f6('Dir: ' + roundToInt(directionScore * 100) + '% Unmap: ' + totalUnmapped + ' | ');
    var positionAccuracy = 0.0;
    var _iterator__ex2g4s_0 = finalMapping.f();
    while (_iterator__ex2g4s_0.g()) {
      var m_0 = _iterator__ex2g4s_0.h();
      var refS = ref.nd_1.o(m_0.pd_1);
      var userS = user.nd_1.o(m_0.qd_1);
      var dist = distance(refS.mc_1, userS.mc_1);
      var tmp_0 = positionAccuracy;
      // Inline function 'kotlin.math.pow' call
      var this_0 = dist * 2.0;
      // Inline function 'kotlin.comparisons.maxOf' call
      var b_5 = 1.0 - Math.pow(this_0, 2);
      positionAccuracy = tmp_0 + Math.max(0.0, b_5);
    }
    positionAccuracy = maxStrokeCount > 0 ? positionAccuracy / maxStrokeCount : 0.0;
    debug.f6('PosAcc: ' + roundToInt(positionAccuracy * 100) + '% | ');
    var relationScore;
    if (finalMapping.i() < 2) {
      relationScore = 0.3;
      debug.f6('Pos: N/A');
    } else {
      var positionMatches = 0.0;
      var totalPositionChecks = 0;
      var inductionVariable_1 = 0;
      var last_1 = finalMapping.i() - 1 | 0;
      if (inductionVariable_1 <= last_1)
        do {
          var i = inductionVariable_1;
          inductionVariable_1 = inductionVariable_1 + 1 | 0;
          var inductionVariable_2 = i + 1 | 0;
          var last_2 = finalMapping.i();
          if (inductionVariable_2 < last_2)
            do {
              var j = inductionVariable_2;
              inductionVariable_2 = inductionVariable_2 + 1 | 0;
              var refRel = ref.od_1.o(finalMapping.o(i).pd_1).o(finalMapping.o(j).pd_1);
              var userRel = user.od_1.o(finalMapping.o(i).qd_1).o(finalMapping.o(j).qd_1);
              totalPositionChecks = totalPositionChecks + 1 | 0;
              var hMatch = refRel.wd_1.equals(userRel.wd_1) ? 1.0 : refRel.wd_1.equals(HorizontalPosition_OVERLAPPING_getInstance()) || userRel.wd_1.equals(HorizontalPosition_OVERLAPPING_getInstance()) ? 0.4 : 0.0;
              var vMatch = refRel.xd_1.equals(userRel.xd_1) ? 1.0 : refRel.xd_1.equals(VerticalPosition_OVERLAPPING_getInstance()) || userRel.xd_1.equals(VerticalPosition_OVERLAPPING_getInstance()) ? 0.4 : 0.0;
              var connMatch = 0.0;
              if (refRel.sd_1 && userRel.sd_1) {
                var pointMatch = refRel.ud_1.equals(userRel.ud_1) ? 1.0 : refRel.ud_1.equals(ConnectionPoint_START_getInstance()) && userRel.ud_1.equals(ConnectionPoint_MIDDLE_getInstance()) || (refRel.ud_1.equals(ConnectionPoint_MIDDLE_getInstance()) && userRel.ud_1.equals(ConnectionPoint_START_getInstance())) || (refRel.ud_1.equals(ConnectionPoint_END_getInstance()) && userRel.ud_1.equals(ConnectionPoint_MIDDLE_getInstance())) || (refRel.ud_1.equals(ConnectionPoint_MIDDLE_getInstance()) && userRel.ud_1.equals(ConnectionPoint_END_getInstance())) ? 0.4 : 0.0;
                var typeMatch = 0.0;
                if (refRel.td_1 === userRel.td_1) {
                  typeMatch = 1.0;
                } else {
                  var refParts = split(refRel.td_1, ['-']);
                  var userParts = split(userRel.td_1, ['-']);
                  if (refParts.i() === 2 && userParts.i() === 2) {
                    if (refParts.o(0) === userParts.o(0) || refParts.o(1) === userParts.o(1)) {
                      typeMatch = 0.5;
                    }
                  }
                }
                // Inline function 'kotlin.math.abs' call
                var x_1 = refRel.vd_1 - userRel.vd_1;
                var qualityMatch = 1.0 - Math.abs(x_1);
                connMatch = pointMatch * 0.5 + typeMatch * 0.3 + qualityMatch * 0.2;
              } else if (!refRel.sd_1 && !userRel.sd_1) {
                connMatch = 1.0;
              }
              var connectionMismatch = !(refRel.sd_1 === userRel.sd_1) ? -0.3 : 0.0;
              var tmp_1 = positionMatches;
              // Inline function 'kotlin.comparisons.maxOf' call
              var b_6 = hMatch * 0.25 + vMatch * 0.25 + connMatch * 0.5 + connectionMismatch;
              positionMatches = tmp_1 + Math.max(0.0, b_6);
            }
             while (inductionVariable_2 < last_2);
        }
         while (inductionVariable_1 <= last_1);
      relationScore = totalPositionChecks > 0 ? positionMatches / totalPositionChecks : 0.3;
      debug.f6('Pos: ' + roundToInt(relationScore * 100) + '%');
    }
    var baseScore = countScore * 0.1 + distScore * 0.1 + directionScore * 0.2 + positionAccuracy * 0.25 + relationScore * 0.35;
    var finalScore = baseScore * unmappedPenalty;
    debug.f6(' | Dist: ' + roundToInt(distScore * 100) + '% Pen: ' + roundToInt(unmappedPenalty * 100) + '%');
    return new ComparisonResult(finalScore, debug.toString());
  }
  function recognizeShape(userPaths, flexible) {
    flexible = flexible === VOID ? false : flexible;
    var tmp0_elvis_lhs = createSignature(userPaths, flexible);
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return emptyList();
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var userSig = tmp;
    var h = 0;
    var v = 0;
    // Inline function 'kotlin.collections.mutableListOf' call
    var angles = ArrayList_init_$Create$_0();
    var _iterator__ex2g4s = userSig.nd_1.f();
    while (_iterator__ex2g4s.g()) {
      var s = _iterator__ex2g4s.h();
      switch (s.jc_1.y1_1) {
        case 0:
          h = h + 1 | 0;
          break;
        case 1:
          v = v + 1 | 0;
          break;
        default:
          break;
      }
      angles.q(roundToInt(s.rc_1));
    }
    var userDebug = 'User: ' + userSig.md_1 + ' [' + h + 'H ' + v + 'V] angles:[' + joinToString(angles, ',') + '] | ';
    // Inline function 'kotlin.collections.mutableListOf' call
    var results = ArrayList_init_$Create$_0();
    var _iterator__ex2g4s_0 = ReferenceData_getInstance().cd_1.f();
    $l$loop_0: while (_iterator__ex2g4s_0.g()) {
      var letter = _iterator__ex2g4s_0.h();
      var tmp2_elvis_lhs = ReferenceData_getInstance().bd_1.u1(letter);
      var tmp_0;
      if (tmp2_elvis_lhs == null) {
        continue $l$loop_0;
      } else {
        tmp_0 = tmp2_elvis_lhs;
      }
      var strokeDefs = tmp_0;
      var tmp3_elvis_lhs = createRefSignature(strokeDefs);
      var tmp_1;
      if (tmp3_elvis_lhs == null) {
        continue $l$loop_0;
      } else {
        tmp_1 = tmp3_elvis_lhs;
      }
      var refSig = tmp_1;
      var _destruct__k2r9zo = compareSignatures(userSig, refSig);
      var score = _destruct__k2r9zo.h7();
      var debug = _destruct__k2r9zo.i7();
      results.q(new RecognitionResult(letter, roundToInt(score * 100), userDebug + debug));
    }
    // Inline function 'kotlin.collections.sortByDescending' call
    if (results.i() > 1) {
      // Inline function 'kotlin.comparisons.compareByDescending' call
      var tmp_2 = recognizeShape$lambda;
      var tmp$ret$3 = new sam$kotlin_Comparator$0_0(tmp_2);
      sortWith(results, tmp$ret$3);
    }
    return results;
  }
  function sam$kotlin_Comparator$0(function_0) {
    this.yd_1 = function_0;
  }
  protoOf(sam$kotlin_Comparator$0).x6 = function (a, b) {
    return this.yd_1(a, b);
  };
  protoOf(sam$kotlin_Comparator$0).compare = function (a, b) {
    return this.x6(a, b);
  };
  protoOf(sam$kotlin_Comparator$0).j2 = function () {
    return this.yd_1;
  };
  protoOf(sam$kotlin_Comparator$0).equals = function (other) {
    var tmp;
    if (!(other == null) ? isInterface(other, Comparator) : false) {
      var tmp_0;
      if (!(other == null) ? isInterface(other, FunctionAdapter) : false) {
        tmp_0 = equals(this.j2(), other.j2());
      } else {
        tmp_0 = false;
      }
      tmp = tmp_0;
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(sam$kotlin_Comparator$0).hashCode = function () {
    return hashCode(this.j2());
  };
  function sam$kotlin_Comparator$0_0(function_0) {
    this.zd_1 = function_0;
  }
  protoOf(sam$kotlin_Comparator$0_0).x6 = function (a, b) {
    return this.zd_1(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_0).compare = function (a, b) {
    return this.x6(a, b);
  };
  protoOf(sam$kotlin_Comparator$0_0).j2 = function () {
    return this.zd_1;
  };
  protoOf(sam$kotlin_Comparator$0_0).equals = function (other) {
    var tmp;
    if (!(other == null) ? isInterface(other, Comparator) : false) {
      var tmp_0;
      if (!(other == null) ? isInterface(other, FunctionAdapter) : false) {
        tmp_0 = equals(this.j2(), other.j2());
      } else {
        tmp_0 = false;
      }
      tmp = tmp_0;
    } else {
      tmp = false;
    }
    return tmp;
  };
  protoOf(sam$kotlin_Comparator$0_0).hashCode = function () {
    return hashCode(this.j2());
  };
  function compareSignatures$typeDistribution(strokes) {
    var h = 0;
    var v = 0;
    var c = 0;
    var _iterator__ex2g4s = strokes.f();
    while (_iterator__ex2g4s.g()) {
      var s = _iterator__ex2g4s.h();
      switch (s.jc_1.y1_1) {
        case 0:
          h = h + 1 | 0;
          break;
        case 1:
          v = v + 1 | 0;
          break;
        case 2:
          c = c + 1 | 0;
          break;
        default:
          noWhenBranchMatchedException();
          break;
      }
    }
    return new Triple(h, v, c);
  }
  function compareSignatures$StrokeMapping(refIdx, userIdx, score) {
    this.pd_1 = refIdx;
    this.qd_1 = userIdx;
    this.rd_1 = score;
  }
  protoOf(compareSignatures$StrokeMapping).toString = function () {
    return 'StrokeMapping(refIdx=' + this.pd_1 + ', userIdx=' + this.qd_1 + ', score=' + this.rd_1 + ')';
  };
  protoOf(compareSignatures$StrokeMapping).hashCode = function () {
    var result = this.pd_1;
    result = imul(result, 31) + this.qd_1 | 0;
    result = imul(result, 31) + getNumberHashCode(this.rd_1) | 0;
    return result;
  };
  protoOf(compareSignatures$StrokeMapping).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof compareSignatures$StrokeMapping))
      return false;
    if (!(this.pd_1 === other.pd_1))
      return false;
    if (!(this.qd_1 === other.qd_1))
      return false;
    if (!equals(this.rd_1, other.rd_1))
      return false;
    return true;
  };
  function compareSignatures$lambda(a, b) {
    // Inline function 'kotlin.comparisons.compareValuesBy' call
    var tmp = b.rd_1;
    var tmp$ret$2 = a.rd_1;
    return compareValues(tmp, tmp$ret$2);
  }
  function recognizeShape$lambda(a, b) {
    // Inline function 'kotlin.comparisons.compareValuesBy' call
    var tmp = b.coverage;
    var tmp$ret$2 = a.coverage;
    return compareValues(tmp, tmp$ret$2);
  }
  function DrawingPoint(x, y) {
    this.x = x;
    this.y = y;
  }
  protoOf(DrawingPoint).ae = function () {
    return this.x;
  };
  protoOf(DrawingPoint).be = function () {
    return this.y;
  };
  protoOf(DrawingPoint).h7 = function () {
    return this.x;
  };
  protoOf(DrawingPoint).i7 = function () {
    return this.y;
  };
  protoOf(DrawingPoint).ce = function (x, y) {
    return new DrawingPoint(x, y);
  };
  protoOf(DrawingPoint).copy = function (x, y, $super) {
    x = x === VOID ? this.x : x;
    y = y === VOID ? this.y : y;
    return $super === VOID ? this.ce(x, y) : $super.ce.call(this, x, y);
  };
  protoOf(DrawingPoint).toString = function () {
    return 'DrawingPoint(x=' + this.x + ', y=' + this.y + ')';
  };
  protoOf(DrawingPoint).hashCode = function () {
    var result = getNumberHashCode(this.x);
    result = imul(result, 31) + getNumberHashCode(this.y) | 0;
    return result;
  };
  protoOf(DrawingPoint).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof DrawingPoint))
      return false;
    if (!equals(this.x, other.x))
      return false;
    if (!equals(this.y, other.y))
      return false;
    return true;
  };
  var StrokeType_LINE_instance;
  var StrokeType_CURVE_instance;
  var StrokeType_CIRCLE_instance;
  function values_1() {
    return [StrokeType_LINE_getInstance(), StrokeType_CURVE_getInstance(), StrokeType_CIRCLE_getInstance()];
  }
  function valueOf_1(value) {
    switch (value) {
      case 'LINE':
        return StrokeType_LINE_getInstance();
      case 'CURVE':
        return StrokeType_CURVE_getInstance();
      case 'CIRCLE':
        return StrokeType_CIRCLE_getInstance();
      default:
        StrokeType_initEntries();
        THROW_IAE('No enum constant experimental.recognition.StrokeType.' + value);
        break;
    }
  }
  var StrokeType_entriesInitialized;
  function StrokeType_initEntries() {
    if (StrokeType_entriesInitialized)
      return Unit_instance;
    StrokeType_entriesInitialized = true;
    StrokeType_LINE_instance = new StrokeType('LINE', 0);
    StrokeType_CURVE_instance = new StrokeType('CURVE', 1);
    StrokeType_CIRCLE_instance = new StrokeType('CIRCLE', 2);
  }
  function StrokeType(name, ordinal) {
    Enum.call(this, name, ordinal);
  }
  function ReferenceStroke(startX, startY, endX, endY, type, controlX, controlY) {
    controlX = controlX === VOID ? null : controlX;
    controlY = controlY === VOID ? null : controlY;
    this.startX = startX;
    this.startY = startY;
    this.endX = endX;
    this.endY = endY;
    this.type = type;
    this.controlX = controlX;
    this.controlY = controlY;
  }
  protoOf(ReferenceStroke).fe = function () {
    return this.startX;
  };
  protoOf(ReferenceStroke).ge = function () {
    return this.startY;
  };
  protoOf(ReferenceStroke).he = function () {
    return this.endX;
  };
  protoOf(ReferenceStroke).ie = function () {
    return this.endY;
  };
  protoOf(ReferenceStroke).je = function () {
    return this.type;
  };
  protoOf(ReferenceStroke).ke = function () {
    return this.controlX;
  };
  protoOf(ReferenceStroke).le = function () {
    return this.controlY;
  };
  protoOf(ReferenceStroke).h7 = function () {
    return this.startX;
  };
  protoOf(ReferenceStroke).i7 = function () {
    return this.startY;
  };
  protoOf(ReferenceStroke).w8 = function () {
    return this.endX;
  };
  protoOf(ReferenceStroke).me = function () {
    return this.endY;
  };
  protoOf(ReferenceStroke).ne = function () {
    return this.type;
  };
  protoOf(ReferenceStroke).oe = function () {
    return this.controlX;
  };
  protoOf(ReferenceStroke).pe = function () {
    return this.controlY;
  };
  protoOf(ReferenceStroke).qe = function (startX, startY, endX, endY, type, controlX, controlY) {
    return new ReferenceStroke(startX, startY, endX, endY, type, controlX, controlY);
  };
  protoOf(ReferenceStroke).copy = function (startX, startY, endX, endY, type, controlX, controlY, $super) {
    startX = startX === VOID ? this.startX : startX;
    startY = startY === VOID ? this.startY : startY;
    endX = endX === VOID ? this.endX : endX;
    endY = endY === VOID ? this.endY : endY;
    type = type === VOID ? this.type : type;
    controlX = controlX === VOID ? this.controlX : controlX;
    controlY = controlY === VOID ? this.controlY : controlY;
    return $super === VOID ? this.qe(startX, startY, endX, endY, type, controlX, controlY) : $super.qe.call(this, startX, startY, endX, endY, type, controlX, controlY);
  };
  protoOf(ReferenceStroke).toString = function () {
    return 'ReferenceStroke(startX=' + this.startX + ', startY=' + this.startY + ', endX=' + this.endX + ', endY=' + this.endY + ', type=' + this.type.toString() + ', controlX=' + this.controlX + ', controlY=' + this.controlY + ')';
  };
  protoOf(ReferenceStroke).hashCode = function () {
    var result = getNumberHashCode(this.startX);
    result = imul(result, 31) + getNumberHashCode(this.startY) | 0;
    result = imul(result, 31) + getNumberHashCode(this.endX) | 0;
    result = imul(result, 31) + getNumberHashCode(this.endY) | 0;
    result = imul(result, 31) + this.type.hashCode() | 0;
    result = imul(result, 31) + (this.controlX == null ? 0 : getNumberHashCode(this.controlX)) | 0;
    result = imul(result, 31) + (this.controlY == null ? 0 : getNumberHashCode(this.controlY)) | 0;
    return result;
  };
  protoOf(ReferenceStroke).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof ReferenceStroke))
      return false;
    if (!equals(this.startX, other.startX))
      return false;
    if (!equals(this.startY, other.startY))
      return false;
    if (!equals(this.endX, other.endX))
      return false;
    if (!equals(this.endY, other.endY))
      return false;
    if (!this.type.equals(other.type))
      return false;
    if (!equals(this.controlX, other.controlX))
      return false;
    if (!equals(this.controlY, other.controlY))
      return false;
    return true;
  };
  function RecognitionResult(letter, coverage, debug) {
    debug = debug === VOID ? '' : debug;
    this.letter = letter;
    this.coverage = coverage;
    this.debug = debug;
  }
  protoOf(RecognitionResult).re = function () {
    return this.letter;
  };
  protoOf(RecognitionResult).se = function () {
    return this.coverage;
  };
  protoOf(RecognitionResult).te = function () {
    return this.debug;
  };
  protoOf(RecognitionResult).h7 = function () {
    return this.letter;
  };
  protoOf(RecognitionResult).i7 = function () {
    return this.coverage;
  };
  protoOf(RecognitionResult).w8 = function () {
    return this.debug;
  };
  protoOf(RecognitionResult).ue = function (letter, coverage, debug) {
    return new RecognitionResult(letter, coverage, debug);
  };
  protoOf(RecognitionResult).copy = function (letter, coverage, debug, $super) {
    letter = letter === VOID ? this.letter : letter;
    coverage = coverage === VOID ? this.coverage : coverage;
    debug = debug === VOID ? this.debug : debug;
    return $super === VOID ? this.ue(letter, coverage, debug) : $super.ue.call(this, letter, coverage, debug);
  };
  protoOf(RecognitionResult).toString = function () {
    return 'RecognitionResult(letter=' + this.letter.toString() + ', coverage=' + this.coverage + ', debug=' + this.debug + ')';
  };
  protoOf(RecognitionResult).hashCode = function () {
    var result = this.letter.hashCode();
    result = imul(result, 31) + this.coverage | 0;
    result = imul(result, 31) + getStringHashCode(this.debug) | 0;
    return result;
  };
  protoOf(RecognitionResult).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof RecognitionResult))
      return false;
    if (!this.letter.equals(other.letter))
      return false;
    if (!(this.coverage === other.coverage))
      return false;
    if (!(this.debug === other.debug))
      return false;
    return true;
  };
  var StrokeDirection_HORIZONTAL_instance;
  var StrokeDirection_VERTICAL_instance;
  var StrokeDirection_CIRCLE_instance;
  var StrokeDirection_entriesInitialized;
  function StrokeDirection_initEntries() {
    if (StrokeDirection_entriesInitialized)
      return Unit_instance;
    StrokeDirection_entriesInitialized = true;
    StrokeDirection_HORIZONTAL_instance = new StrokeDirection('HORIZONTAL', 0);
    StrokeDirection_VERTICAL_instance = new StrokeDirection('VERTICAL', 1);
    StrokeDirection_CIRCLE_instance = new StrokeDirection('CIRCLE', 2);
  }
  function StrokeDirection(name, ordinal) {
    Enum.call(this, name, ordinal);
  }
  function AnalyzedStroke(direction, startPoint, endPoint, centerPoint, minX, maxX, minY, maxY, angle, length, isCurved, flexDirection) {
    flexDirection = flexDirection === VOID ? false : flexDirection;
    this.jc_1 = direction;
    this.kc_1 = startPoint;
    this.lc_1 = endPoint;
    this.mc_1 = centerPoint;
    this.nc_1 = minX;
    this.oc_1 = maxX;
    this.pc_1 = minY;
    this.qc_1 = maxY;
    this.rc_1 = angle;
    this.sc_1 = length;
    this.tc_1 = isCurved;
    this.uc_1 = flexDirection;
  }
  protoOf(AnalyzedStroke).ve = function (direction, startPoint, endPoint, centerPoint, minX, maxX, minY, maxY, angle, length, isCurved, flexDirection) {
    return new AnalyzedStroke(direction, startPoint, endPoint, centerPoint, minX, maxX, minY, maxY, angle, length, isCurved, flexDirection);
  };
  protoOf(AnalyzedStroke).yc = function (direction, startPoint, endPoint, centerPoint, minX, maxX, minY, maxY, angle, length, isCurved, flexDirection, $super) {
    direction = direction === VOID ? this.jc_1 : direction;
    startPoint = startPoint === VOID ? this.kc_1 : startPoint;
    endPoint = endPoint === VOID ? this.lc_1 : endPoint;
    centerPoint = centerPoint === VOID ? this.mc_1 : centerPoint;
    minX = minX === VOID ? this.nc_1 : minX;
    maxX = maxX === VOID ? this.oc_1 : maxX;
    minY = minY === VOID ? this.pc_1 : minY;
    maxY = maxY === VOID ? this.qc_1 : maxY;
    angle = angle === VOID ? this.rc_1 : angle;
    length = length === VOID ? this.sc_1 : length;
    isCurved = isCurved === VOID ? this.tc_1 : isCurved;
    flexDirection = flexDirection === VOID ? this.uc_1 : flexDirection;
    return $super === VOID ? this.ve(direction, startPoint, endPoint, centerPoint, minX, maxX, minY, maxY, angle, length, isCurved, flexDirection) : $super.ve.call(this, direction, startPoint, endPoint, centerPoint, minX, maxX, minY, maxY, angle, length, isCurved, flexDirection);
  };
  protoOf(AnalyzedStroke).toString = function () {
    return 'AnalyzedStroke(direction=' + this.jc_1.toString() + ', startPoint=' + this.kc_1.toString() + ', endPoint=' + this.lc_1.toString() + ', centerPoint=' + this.mc_1.toString() + ', minX=' + this.nc_1 + ', maxX=' + this.oc_1 + ', minY=' + this.pc_1 + ', maxY=' + this.qc_1 + ', angle=' + this.rc_1 + ', length=' + this.sc_1 + ', isCurved=' + this.tc_1 + ', flexDirection=' + this.uc_1 + ')';
  };
  protoOf(AnalyzedStroke).hashCode = function () {
    var result = this.jc_1.hashCode();
    result = imul(result, 31) + this.kc_1.hashCode() | 0;
    result = imul(result, 31) + this.lc_1.hashCode() | 0;
    result = imul(result, 31) + this.mc_1.hashCode() | 0;
    result = imul(result, 31) + getNumberHashCode(this.nc_1) | 0;
    result = imul(result, 31) + getNumberHashCode(this.oc_1) | 0;
    result = imul(result, 31) + getNumberHashCode(this.pc_1) | 0;
    result = imul(result, 31) + getNumberHashCode(this.qc_1) | 0;
    result = imul(result, 31) + getNumberHashCode(this.rc_1) | 0;
    result = imul(result, 31) + getNumberHashCode(this.sc_1) | 0;
    result = imul(result, 31) + getBooleanHashCode(this.tc_1) | 0;
    result = imul(result, 31) + getBooleanHashCode(this.uc_1) | 0;
    return result;
  };
  protoOf(AnalyzedStroke).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof AnalyzedStroke))
      return false;
    if (!this.jc_1.equals(other.jc_1))
      return false;
    if (!this.kc_1.equals(other.kc_1))
      return false;
    if (!this.lc_1.equals(other.lc_1))
      return false;
    if (!this.mc_1.equals(other.mc_1))
      return false;
    if (!equals(this.nc_1, other.nc_1))
      return false;
    if (!equals(this.oc_1, other.oc_1))
      return false;
    if (!equals(this.pc_1, other.pc_1))
      return false;
    if (!equals(this.qc_1, other.qc_1))
      return false;
    if (!equals(this.rc_1, other.rc_1))
      return false;
    if (!equals(this.sc_1, other.sc_1))
      return false;
    if (!(this.tc_1 === other.tc_1))
      return false;
    if (!(this.uc_1 === other.uc_1))
      return false;
    return true;
  };
  var HorizontalPosition_LEFT_OF_instance;
  var HorizontalPosition_RIGHT_OF_instance;
  var HorizontalPosition_OVERLAPPING_instance;
  var HorizontalPosition_entriesInitialized;
  function HorizontalPosition_initEntries() {
    if (HorizontalPosition_entriesInitialized)
      return Unit_instance;
    HorizontalPosition_entriesInitialized = true;
    HorizontalPosition_LEFT_OF_instance = new HorizontalPosition('LEFT_OF', 0);
    HorizontalPosition_RIGHT_OF_instance = new HorizontalPosition('RIGHT_OF', 1);
    HorizontalPosition_OVERLAPPING_instance = new HorizontalPosition('OVERLAPPING', 2);
  }
  function HorizontalPosition(name, ordinal) {
    Enum.call(this, name, ordinal);
  }
  var VerticalPosition_ABOVE_instance;
  var VerticalPosition_BELOW_instance;
  var VerticalPosition_OVERLAPPING_instance;
  var VerticalPosition_entriesInitialized;
  function VerticalPosition_initEntries() {
    if (VerticalPosition_entriesInitialized)
      return Unit_instance;
    VerticalPosition_entriesInitialized = true;
    VerticalPosition_ABOVE_instance = new VerticalPosition('ABOVE', 0);
    VerticalPosition_BELOW_instance = new VerticalPosition('BELOW', 1);
    VerticalPosition_OVERLAPPING_instance = new VerticalPosition('OVERLAPPING', 2);
  }
  function VerticalPosition(name, ordinal) {
    Enum.call(this, name, ordinal);
  }
  var ConnectionPoint_START_instance;
  var ConnectionPoint_MIDDLE_instance;
  var ConnectionPoint_END_instance;
  var ConnectionPoint_NONE_instance;
  var ConnectionPoint_entriesInitialized;
  function ConnectionPoint_initEntries() {
    if (ConnectionPoint_entriesInitialized)
      return Unit_instance;
    ConnectionPoint_entriesInitialized = true;
    ConnectionPoint_START_instance = new ConnectionPoint('START', 0);
    ConnectionPoint_MIDDLE_instance = new ConnectionPoint('MIDDLE', 1);
    ConnectionPoint_END_instance = new ConnectionPoint('END', 2);
    ConnectionPoint_NONE_instance = new ConnectionPoint('NONE', 3);
  }
  function ConnectionPoint(name, ordinal) {
    Enum.call(this, name, ordinal);
  }
  function StrokeRelation(connected, connectionType, connectionPointOnStroke2, connectionQuality, horizontalPosition, verticalPosition) {
    this.sd_1 = connected;
    this.td_1 = connectionType;
    this.ud_1 = connectionPointOnStroke2;
    this.vd_1 = connectionQuality;
    this.wd_1 = horizontalPosition;
    this.xd_1 = verticalPosition;
  }
  protoOf(StrokeRelation).toString = function () {
    return 'StrokeRelation(connected=' + this.sd_1 + ', connectionType=' + this.td_1 + ', connectionPointOnStroke2=' + this.ud_1.toString() + ', connectionQuality=' + this.vd_1 + ', horizontalPosition=' + this.wd_1.toString() + ', verticalPosition=' + this.xd_1.toString() + ')';
  };
  protoOf(StrokeRelation).hashCode = function () {
    var result = getBooleanHashCode(this.sd_1);
    result = imul(result, 31) + getStringHashCode(this.td_1) | 0;
    result = imul(result, 31) + this.ud_1.hashCode() | 0;
    result = imul(result, 31) + getNumberHashCode(this.vd_1) | 0;
    result = imul(result, 31) + this.wd_1.hashCode() | 0;
    result = imul(result, 31) + this.xd_1.hashCode() | 0;
    return result;
  };
  protoOf(StrokeRelation).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof StrokeRelation))
      return false;
    if (!(this.sd_1 === other.sd_1))
      return false;
    if (!(this.td_1 === other.td_1))
      return false;
    if (!this.ud_1.equals(other.ud_1))
      return false;
    if (!equals(this.vd_1, other.vd_1))
      return false;
    if (!this.wd_1.equals(other.wd_1))
      return false;
    if (!this.xd_1.equals(other.xd_1))
      return false;
    return true;
  };
  function StructuralSignature(strokeCount, strokes, relations) {
    this.md_1 = strokeCount;
    this.nd_1 = strokes;
    this.od_1 = relations;
  }
  protoOf(StructuralSignature).toString = function () {
    return 'StructuralSignature(strokeCount=' + this.md_1 + ', strokes=' + toString_0(this.nd_1) + ', relations=' + toString_0(this.od_1) + ')';
  };
  protoOf(StructuralSignature).hashCode = function () {
    var result = this.md_1;
    result = imul(result, 31) + hashCode(this.nd_1) | 0;
    result = imul(result, 31) + hashCode(this.od_1) | 0;
    return result;
  };
  protoOf(StructuralSignature).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof StructuralSignature))
      return false;
    if (!(this.md_1 === other.md_1))
      return false;
    if (!equals(this.nd_1, other.nd_1))
      return false;
    if (!equals(this.od_1, other.od_1))
      return false;
    return true;
  };
  function BoundingBox(minX, minY, maxX, maxY) {
    this.dc_1 = minX;
    this.ec_1 = minY;
    this.fc_1 = maxX;
    this.gc_1 = maxY;
  }
  protoOf(BoundingBox).hc = function () {
    return this.fc_1 - this.dc_1;
  };
  protoOf(BoundingBox).ic = function () {
    return this.gc_1 - this.ec_1;
  };
  protoOf(BoundingBox).toString = function () {
    return 'BoundingBox(minX=' + this.dc_1 + ', minY=' + this.ec_1 + ', maxX=' + this.fc_1 + ', maxY=' + this.gc_1 + ')';
  };
  protoOf(BoundingBox).hashCode = function () {
    var result = getNumberHashCode(this.dc_1);
    result = imul(result, 31) + getNumberHashCode(this.ec_1) | 0;
    result = imul(result, 31) + getNumberHashCode(this.fc_1) | 0;
    result = imul(result, 31) + getNumberHashCode(this.gc_1) | 0;
    return result;
  };
  protoOf(BoundingBox).equals = function (other) {
    if (this === other)
      return true;
    if (!(other instanceof BoundingBox))
      return false;
    if (!equals(this.dc_1, other.dc_1))
      return false;
    if (!equals(this.ec_1, other.ec_1))
      return false;
    if (!equals(this.fc_1, other.fc_1))
      return false;
    if (!equals(this.gc_1, other.gc_1))
      return false;
    return true;
  };
  function StrokeType_LINE_getInstance() {
    StrokeType_initEntries();
    return StrokeType_LINE_instance;
  }
  function StrokeType_CURVE_getInstance() {
    StrokeType_initEntries();
    return StrokeType_CURVE_instance;
  }
  function StrokeType_CIRCLE_getInstance() {
    StrokeType_initEntries();
    return StrokeType_CIRCLE_instance;
  }
  function StrokeDirection_HORIZONTAL_getInstance() {
    StrokeDirection_initEntries();
    return StrokeDirection_HORIZONTAL_instance;
  }
  function StrokeDirection_VERTICAL_getInstance() {
    StrokeDirection_initEntries();
    return StrokeDirection_VERTICAL_instance;
  }
  function StrokeDirection_CIRCLE_getInstance() {
    StrokeDirection_initEntries();
    return StrokeDirection_CIRCLE_instance;
  }
  function HorizontalPosition_LEFT_OF_getInstance() {
    HorizontalPosition_initEntries();
    return HorizontalPosition_LEFT_OF_instance;
  }
  function HorizontalPosition_RIGHT_OF_getInstance() {
    HorizontalPosition_initEntries();
    return HorizontalPosition_RIGHT_OF_instance;
  }
  function HorizontalPosition_OVERLAPPING_getInstance() {
    HorizontalPosition_initEntries();
    return HorizontalPosition_OVERLAPPING_instance;
  }
  function VerticalPosition_ABOVE_getInstance() {
    VerticalPosition_initEntries();
    return VerticalPosition_ABOVE_instance;
  }
  function VerticalPosition_BELOW_getInstance() {
    VerticalPosition_initEntries();
    return VerticalPosition_BELOW_instance;
  }
  function VerticalPosition_OVERLAPPING_getInstance() {
    VerticalPosition_initEntries();
    return VerticalPosition_OVERLAPPING_instance;
  }
  function ConnectionPoint_START_getInstance() {
    ConnectionPoint_initEntries();
    return ConnectionPoint_START_instance;
  }
  function ConnectionPoint_MIDDLE_getInstance() {
    ConnectionPoint_initEntries();
    return ConnectionPoint_MIDDLE_instance;
  }
  function ConnectionPoint_END_getInstance() {
    ConnectionPoint_initEntries();
    return ConnectionPoint_END_instance;
  }
  function ConnectionPoint_NONE_getInstance() {
    ConnectionPoint_initEntries();
    return ConnectionPoint_NONE_instance;
  }
  //region block: post-declaration
  defineProp(protoOf(Hangul), 'consonants', protoOf(Hangul).g9);
  defineProp(protoOf(Hangul), 'vowels', protoOf(Hangul).h9);
  defineProp(protoOf(Hangul), 'complexVowels', protoOf(Hangul).i9);
  defineProp(protoOf(Hangul), 'tenseConsonants', protoOf(Hangul).j9);
  defineProp(protoOf(RemovePolicy), 'name', protoOf(RemovePolicy).z1);
  defineProp(protoOf(RemovePolicy), 'ordinal', protoOf(RemovePolicy).a2);
  defineProp(protoOf(LetterCategory), 'name', protoOf(LetterCategory).z1);
  defineProp(protoOf(LetterCategory), 'ordinal', protoOf(LetterCategory).a2);
  defineProp(protoOf(StrokeType), 'name', protoOf(StrokeType).z1);
  defineProp(protoOf(StrokeType), 'ordinal', protoOf(StrokeType).a2);
  //endregion
  //region block: init
  Hangul_instance = new Hangul();
  //endregion
  //region block: exports
  function $jsExportAll$(_) {
    defineProp(_, 'Hangul', Hangul_getInstance, VOID, true);
    _.RemovePolicy = RemovePolicy;
    _.RemovePolicy.values = values;
    _.RemovePolicy.valueOf = valueOf;
    defineProp(_.RemovePolicy, 'DEFAULT', RemovePolicy_DEFAULT_getInstance, VOID, true);
    defineProp(_.RemovePolicy, 'REFORMAT_ON_DELETE', RemovePolicy_REFORMAT_ON_DELETE_getInstance, VOID, true);
    _.HangulContext = HangulContext;
    _.LetterCategory = LetterCategory;
    _.LetterCategory.values = values_0;
    _.LetterCategory.valueOf = valueOf_0;
    defineProp(_.LetterCategory, 'CONSONANT', LetterCategory_CONSONANT_getInstance, VOID, true);
    defineProp(_.LetterCategory, 'TENSE_CONSONANT', LetterCategory_TENSE_CONSONANT_getInstance, VOID, true);
    defineProp(_.LetterCategory, 'VOWEL', LetterCategory_VOWEL_getInstance, VOID, true);
    defineProp(_.LetterCategory, 'COMPLEX_VOWEL', LetterCategory_COMPLEX_VOWEL_getInstance, VOID, true);
    _.Letter = Letter;
    defineProp(_, 'Letters', Letters_getInstance, VOID, true);
    var experimental = _.experimental || (_.experimental = {});
    var recognition = experimental.recognition || (experimental.recognition = {});
    recognition.HangulRecognizer = HangulRecognizer;
    var experimental_0 = _.experimental || (_.experimental = {});
    var recognition_0 = experimental_0.recognition || (experimental_0.recognition = {});
    recognition_0.DrawingPoint = DrawingPoint;
    recognition_0.StrokeType = StrokeType;
    recognition_0.StrokeType.values = values_1;
    recognition_0.StrokeType.valueOf = valueOf_1;
    defineProp(recognition_0.StrokeType, 'LINE', StrokeType_LINE_getInstance, VOID, true);
    defineProp(recognition_0.StrokeType, 'CURVE', StrokeType_CURVE_getInstance, VOID, true);
    defineProp(recognition_0.StrokeType, 'CIRCLE', StrokeType_CIRCLE_getInstance, VOID, true);
    recognition_0.ReferenceStroke = ReferenceStroke;
    recognition_0.RecognitionResult = RecognitionResult;
  }
  $jsExportAll$(_);
  kotlin_kotlin.$jsExportAll$(_);
  //endregion
  return _;
}));

//# sourceMappingURL=khangul.js.map
