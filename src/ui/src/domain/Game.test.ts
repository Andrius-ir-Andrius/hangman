import Game from "./Game";

test("should wrong letters count be 0 when empty", () => {
    expect(new Game('____', [], -1).wronglyGuessedCount()).toBe(0)
})

test("should count all guessed letters as wrong", () => {
    expect(new Game('____', ['a', 'b', 'c'], -1).wronglyGuessedCount()).toBe(3)
})

test("should count guessed letters not in word as wrong", () => {
    expect(new Game('a__a', ['a', 'b', 'c'], -1).wronglyGuessedCount()).toBe(2)
})

test("game should not be finished", () => {
    expect(new Game('a__a', [], -1).hasFinished()).toBeFalsy();
})

test("game should be finished", () => {
    expect(new Game('arfa', [], -1).hasFinished()).toBeTruthy();
})