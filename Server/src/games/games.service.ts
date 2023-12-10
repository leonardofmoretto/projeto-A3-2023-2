import { Injectable } from '@nestjs/common';

@Injectable()
export class GamesService {
    async findOne(id: number) {
        try {
            const response = await fetch(`https://store.steampowered.com/api/appdetails?appids=${id}`, {
                headers: {
                    'Accept-Language': "pt-BR",
                },
            });

            if (!response.ok) {
                throw new Error('Network response was not ok');
            }

            const data = await response.json();
            return data;
        } catch (error) {
            console.error(error);
            throw new Error('Error fetching game details');
        }
    }
}
