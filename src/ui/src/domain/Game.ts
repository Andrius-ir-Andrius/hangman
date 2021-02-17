const URL = 'http://localhost:4567/game'
type responseGame = {
    word: string,
    guessedLetters: string[]
}

export default class Game {
    private readonly word: string;
    private readonly guessedLetters: string[];
    private readonly id: number;

    private constructor(word: string, guessedLetters: string[], id: number) {
        this.word = word;
        this.guessedLetters = guessedLetters
        this.id = id
    }

    getWord(): string {
        return this.word
    }

    getGuessedLetters(): string[] {
        return this.guessedLetters
    }

    static async createGame(): Promise<number> {
        return await (await fetch(URL, {method: 'POST'})).json().catch(() => {
            throw new DOMException('Failed to load game')
        })
    }

    static async loadGame(id: number): Promise<Game> {
        let game: responseGame = await (await fetch(URL + '?id=' + id,)).json().catch(() => {
            throw new DOMException('Failed to load game')
        })
        return new Game(game.word, game.guessedLetters, id)
    }

    async guessLetter(letter: string): Promise<Game> {
        let game: responseGame = await (await fetch(URL, {
            method: 'PUT', body: JSON.stringify({
                id: this.id
                , letter
            })
        })).json().catch(() => {
            throw new DOMException('Failed to guess')
        })
        return new Game(game.word, game.guessedLetters, this.id)
    }
}