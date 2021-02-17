import Game from "../domain/Game";

const URL = 'http://localhost:4567/game'
type responseGame = {
    word: string,
    guessedLetters: string[]
}

export async function createGame(): Promise<number> {
    return await (await fetch(URL, {method: 'POST'})).json().catch(() => {
        throw new DOMException('Failed to load game')
    })
}

export async function loadGame(id: number): Promise<Game> {
    let game: responseGame = await (await fetch(URL + '?id=' + id)).json().catch(() => {
        throw new DOMException('Failed to load game')
    })
    return new Game(game.word, game.guessedLetters, id)
}

export async function guessLetter(id: number, letter: string): Promise<Game> {
    let game: responseGame = await (await fetch(URL, {
        method: 'PUT', body: JSON.stringify({
            id,
            letter
        })
    })).json().catch(() => {
        throw new DOMException('Failed to guess')
    })
    return new Game(game.word, game.guessedLetters, id)
}