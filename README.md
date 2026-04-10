# Khangul ![npm](https://img.shields.io/npm/v/khangul) ![npm bundle size](https://img.shields.io/bundlephobia/minzip/khangul)

Multiplatform Hangul processor library for Kotlin &amp; JS projects, based on reverse-engineered Branah keyboard algorithm.
Supported platforms:

* JS (Node, Browser)
* Kotlin (JVM, JS, Native)

### Usage

Install [khangul](https://www.npmjs.com/package/khangul) npm package:

```bash
$ npm i khangul
```

Import `HangulContext`:

```typescript
import { HangulContext } from 'khangul'
```

Create a new instance of `HangulContext` and use it to process Hangul:

```typescript
const hangulContext = new HangulContext()
console.log(hangulContext.getValue()) // ''

hangulContext.appendLetter('ㅇ')
hangulContext.appendLetter('ㅏ')
hangulContext.appendLetter('ㄴ')
console.log(hangulContext.getValue()) // 안

hangulContext.removeLastLetter()
console.log(hangulContext.getValue()) // 아
```

### Utilities

Each jamo is exposed as a `Letter` with `character`, `name`, `romanization`, and `category`.
The `Letters` registry provides them as named constants and grouped lists:

```typescript
Letters.ㄱ.character     // 'ㄱ'
Letters.ㄱ.name          // 'giyeok'
Letters.ㄱ.romanization  // ['g', 'k']

Letters.consonants       // [ㄱ, ㄴ, ㄷ, ...]
Letters.tenseConsonants  // [ㄲ, ㄸ, ㅃ, ...]
Letters.vowels           // [ㅏ, ㅑ, ㅓ, ...]
Letters.complexVowels    // [ㅐ, ㅔ, ㅒ, ...]

Letters.findByCharacter('ㅎ')
Letters.getAll()
// output: [ ...consonants, ...tenseConsonants, ...vowels, ...complexVowels ]

Hangul.generateRandomSyllable()
// example output: 벼, 으, 뷰, 티, 이, 포, 쥬, 슜, 우
```

### Visual recognition (experimental)

`HangulRecognizer` identifies hand-drawn jamo from canvas paths and validates
guided stroke-by-stroke input. A live demo is available at
[dzikoysk.github.io/khangul](https://dzikoysk.github.io/khangul).

```typescript
import { Letters, experimental } from 'khangul'

const recognizer = new experimental.recognition.HangulRecognizer()
```

**Free draw** — pass an array of strokes (each stroke is a sequence of `{x, y}`
points captured from a canvas) and get back a ranked list of letter matches with
a 0–100 coverage score:

```typescript
const paths = [[{x: 20, y: 25}, {x: 80, y: 25}, {x: 80, y: 80}]]
const results = recognizer.recognizeFromArrays(paths)
results[0].letter.character  // 'ㄱ'
results[0].coverage          // 0–100
```

**Guided mode** — for tutorial-style UIs where the user is asked to draw a
specific letter one step at a time. Validates that the points the user just
drew match the expected shape and position of step `stepIndex` of the target
letter, scaled to your canvas size in pixels:

```typescript
recognizer.validateGuidedStrokeFromArray(userPoints, Letters.ㄱ, 0, canvasSize)
```

**Reference geometry** — query the built-in stroke templates for any letter.
Each letter is broken down into a list of "steps" (one pen-down/up action),
and each step contains one or more connected segments. Useful for rendering
guides or animating stroke order:

```typescript
recognizer.getStepCount(Letters.ㄹ)         // 3 — ㄹ takes three pen strokes
recognizer.getStepSegments(Letters.ㄹ, 0)   // segments drawn during step 0
recognizer.sampleReferenceStroke(stroke, 40) // 40 evenly-spaced points along a segment
```

### Use-cases

The library handles natural Hangul input, so its main use-case is to be used in text editors, chat apps, etc.
As an example, there's a preview of [react-simple-keyboard](https://github.com/hodgef/react-simple-keyboard) component integrated with our Hangul context & compatibile keyboard layout:

```tsx
import { HangulContext } from "khangul"
import Keyboard from 'react-simple-keyboard'

const hangulContext = useRef(new HangulContext())
const keyboard = useRef(null as unknown as SimpleKeyboard)
const [userInput, setUserInput] = useState('')
const [layoutName, setLayoutName] = useState('default')

const onKeyPress = (button: string) => {
    if (button === "{shift}" || button === "{lock}") {
        setLayoutName(layoutName === "default" ? "shift" : "default")
        return
    }
    else if (button === "{bksp}") {
        hangulContext.current.removeLastLetter()
        setUserInput(hangulContext.current.getValue())
        return
    }
    hangulContext.current.appendLetter(button)
    setUserInput(hangulContext.current.getValue())
}

return (
    <>
        <Input
            placeholder='Type here...'
            value={userInput}
            onChange={() => {}}
        />
        <Keyboard
            keyboardRef={ref => (keyboard.current = ref)}
            enableLayoutCandidates={false}
            layoutName={layoutName}
            onChange={() => {}}
            onKeyPress={onKeyPress}
            layout={{
                default: [
                    "` 1 2 3 4 5 6 7 8 9 0 - = {bksp}",
                    "{tab} ㅂ ㅈ ㄷ ㄱ ㅅ ㅛ ㅕ ㅑ ㅐ ㅔ [ ] \\",
                    "{lock} ㅁ ㄴ ㅇ ㄹ ㅎ ㅗ ㅓ ㅏ ㅣ ; ' {enter}",
                    "{shift} ㅋ ㅌ ㅊ ㅍ ㅠ ㅜ ㅡ , . / {shift}",
                    ".com @ {space}",
                ],
                    shift: [
                    "~ ! @ # $ % ^ & * ( ) _ + {bksp}",
                    "{tab} ㅃ ㅉ ㄸ ㄲ ㅆ    ㅒ ㅖ { } |",
                    '{lock}         : " {enter}',
                    "{shift} |       < > ? {shift}",
                    ".com @ {space}",
                ],
            }}
        />
    </>
)
```
