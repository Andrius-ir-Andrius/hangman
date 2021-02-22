export default class Game {
  private readonly word: string;
  private readonly guessedLetters: string[];
  private readonly id: number;

  public constructor(word: string, guessedLetters: string[], id: number) {
    this.word = word;
    this.guessedLetters = guessedLetters;
    this.id = id;
  }

  getId(): number {
    return this.id;
  }

  getWord(): string {
    return this.word;
  }

  getGuessedLetters(): string[] {
    return this.guessedLetters;
  }

  wronglyGuessedCount(): number {
    return this.guessedLetters.filter((e) => !this.word.includes(e)).length;
  }

  hasFinished(): boolean {
    return this.wronglyGuessedCount() >= 10 || !this.getWord().includes("_");
  }
}
