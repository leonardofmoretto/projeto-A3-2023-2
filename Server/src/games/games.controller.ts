import { Controller, Get, Query } from '@nestjs/common';
import { GamesService } from './games.service';

@Controller('games')
export class GamesController {
    constructor(private readonly gamesService: GamesService) {}
    @Get('gameId')
    findOneById(@Query('id') id: number) {
      return this.gamesService.findOneById(+id);
    }

    @Get('gameName')
    findOneByName(@Query('name') name: string){
      return this.gamesService.findOneByName(name);
    }
}
