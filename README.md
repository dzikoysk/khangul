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

khangulContext.appendLetter('ㅇ')
khangulContext.appendLetter('ㅏ')
khangulContext.appendLetter('ㄴ')
console.log(hangulContext.getValue()) // 안

khangulContext.removeLastLetter()
console.log(hangulContext.getValue()) // 아
```

### Use-cases

The library handles natural Hangul input, so it's main use-case is to be used in text editors, chat apps, etc.
As an example,

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